package com.springboot.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.request.BookingCaptureRequest;
import com.springboot.booking.dto.request.BookingDetailRequest;
import com.springboot.booking.dto.request.BookingRequest;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.entities.Room;
import com.springboot.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static vn.library.common.utils.TokenUtil.basicToken;

@Service
@RequiredArgsConstructor
public class PaymentService {
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    @Value("${currency.rate.usd}")
    private String currencyRate;
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.secret}")
    private String secret;

    private final RoomRepository roomRepository;
    private final UserService userService;

    private final ObjectMapper mapper;

    public Map<String, Object> createOrder(BookingRequest request, Principal principal) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("purchase_units", getPurchaseUnits(request));
        requestBody.put("intent", "CAPTURE");
        requestBody.put("payer", getPayer(principal));
        requestBody.put("payment_source", getPaymentSource());

        Map<String, Object> token = generateTokenPaypal();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("%s %s", token.get("token_type"), token.get("access_token")));

        String response = WebClient
                .create()
                .post()
                .uri("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(mapper.writeValueAsString(requestBody)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return mapper.readValue(response, Map.class);
    }

    public Map<String, Object> captureOrder(BookingCaptureRequest request) throws JsonProcessingException {
        Map<String, Object> token = generateTokenPaypal();

        WebClient webClient = WebClient.create();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("%s %s", token.get("token_type"), token.get("access_token")));

        String response = webClient.post()
                .uri(String.format("https://api-m.sandbox.paypal.com/v2/checkout/orders/%s/capture", request.getPaymentId()))
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return mapper.readValue(response, Map.class);
    }

    private Map<String, Object> generateTokenPaypal() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", basicToken(clientId, secret));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");

        String response = WebClient
                .create()
                .post()
                .uri("https://api-m.sandbox.paypal.com/v1/oauth2/token")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return mapper.readValue(response, Map.class);
    }

    private Map<String, Object> getPayer(Principal principal) {
        UserDto userDto = userService.getCurrentUser(principal);

        Map<String, Object> name = new HashMap<>();
        name.put("given_name", userDto.getFirstName());
        name.put("surname", userDto.getLastName());

        Map<String, Object> payer = new HashMap<>();
        payer.put("name", name);
        return payer;
    }

    private List<Map<String, Object>> getPurchaseUnits(BookingRequest request) {
        List<Map<String, Object>> purchaseUnits = new ArrayList<>();
        Map<String, Object> amount = new HashMap<>();
        Map<String, Object> breakdown = new HashMap<>();
        Map<String, Object> itemTotal = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> unit = new HashMap<>();

        int subDate = DatetimeUtil.subLocalDate(request.getFromDate(), request.getToDate());
        double totalPrice = 0.0;

        for (BookingDetailRequest bookingDetail : request.getCartItems()) {
            Room room = roomRepository.findById(bookingDetail.getRoomId())
                    .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "phòng"));
            double price = calculateBookingDetailPrice(room.getPrice(), room.getDiscountPercent(), subDate);
            totalPrice += price * bookingDetail.getQuantity();

            Map<String, Object> unitAmount = new HashMap<>();
            unitAmount.put("currency_code", "USD");
            unitAmount.put("value", price);

            Map<String, Object> item = new HashMap<>();
            item.put("name", room.getRoomType());
            item.put("quantity", bookingDetail.getQuantity());
            item.put("unit_amount", unitAmount);

            items.add(item);
        }

        itemTotal.put("currency_code", "USD");
        itemTotal.put("value", totalPrice);

        breakdown.put("item_total", itemTotal);

        amount.put("currency_code", "USD");
        amount.put("value", totalPrice);
        amount.put("breakdown", breakdown);

        unit.put("items", items);
        unit.put("amount", amount);

        purchaseUnits.add(unit);

        return purchaseUnits;
    }

    private double calculateBookingDetailPrice(double roomPrice, double discountPercent, int subDate) {
        double subPrice = ((roomPrice * ((100 - discountPercent) / 100) * subDate) / Double.parseDouble(currencyRate));
        return Double.parseDouble(decimalFormat.format(subPrice));
    }

    private Map<String, Object> getPaymentSource() {
        Map<String, Object> experienceContext = new HashMap<>();
        experienceContext.put("payment_method_preference", "IMMEDIATE_PAYMENT_REQUIRED");
        experienceContext.put("brand_name", "EXAMPLE INC");
        experienceContext.put("locale", "en-US");
        experienceContext.put("landing_page", "LOGIN");
        experienceContext.put("shipping_preference", "NO_SHIPPING");
        experienceContext.put("user_action", "PAY_NOW");

        Map<String, Object> paypal = new HashMap<>();
        paypal.put("experience_context", experienceContext);

        Map<String, Object> paymentSource = new HashMap<>();
        paymentSource.put("paypal", paypal);

        return paymentSource;
    }
}
