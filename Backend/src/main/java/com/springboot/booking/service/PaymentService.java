package com.springboot.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.BookingCaptureRequest;
import com.springboot.booking.dto.request.BookingDetailRequest;
import com.springboot.booking.dto.request.BookingPaymentRequest;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${environment.local.base-url}")
    private String baseUrl;
    final double RATE_CONVERT = 23810;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");
    @Value("${paypal.client-id}")
    private String clientId;
    @Value("${paypal.secret}")
    private String secret;
    @Value("${paypal.mode}")
    private String mode;

    private final APIContext apiContext;
    private final UserService userService;
    private final AccommodationService accommodationService;
    private final RoomService roomService;

    private final ObjectMapper mapper;

    public Map<String, Object> createOrder(BookingPaymentRequest request) throws JsonProcessingException {
        Map<String, Object> token = generateTokenPaypal();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("purchase_units", getPurchaseUnits(request));
        requestBody.put("intent", "CAPTURE");
        requestBody.put("payer", getPayer());
        requestBody.put("payment_source", getPaymentSource());

        WebClient webClient = WebClient.create();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", String.format("%s %s", token.get("token_type"), token.get("access_token")));

        String response = webClient.post()
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
        System.out.println(response);
        return mapper.readValue(response, Map.class);
    }

    private Map<String, Object> generateTokenPaypal() throws JsonProcessingException {
        WebClient webClient = WebClient.create();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", Util.getBasicAuth(clientId, secret));

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");

        String response = webClient.post()
                .uri("https://api-m.sandbox.paypal.com/v1/oauth2/token")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromFormData(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return mapper.readValue(response, Map.class);
    }

    private Map<String, Object> getPayer() {
        UserResponse user = userService.getCurrentUser();
        Map<String, Object> name = new HashMap<>();
        name.put("given_name", user.getFirstName());
        name.put("surname", user.getLastName());

        Map<String, Object> payer = new HashMap<>();
        payer.put("name", name);
        return payer;
    }

    private List<Map<String, Object>> getPurchaseUnits(BookingPaymentRequest request) {
        List<Map<String, Object>> purchaseUnits = new ArrayList<>();
        Map<String, Object> unit = new HashMap<>();
        Map<String, Object> amount = new HashMap<>();
        List<Map<String, Object>> items = new ArrayList<>();

        int subDate = DatetimeUtil.subLocalDate(request.getFromDate(), request.getToDate());
        Double totalPrice = 0.0;

        for (BookingDetailRequest bookingDetail : request.getCartItems()) {
            RoomResponse room = roomService.getById(bookingDetail.getRoomId());
            double price = (room.getPrice() * ((100 - room.getDiscountPercent()) / 100) * bookingDetail.getQuantity() * subDate) / RATE_CONVERT;
            totalPrice += price;

            Map<String, Object> unitAmount = new HashMap<>();
            unitAmount.put("currency_code", "USD");
            unitAmount.put("value", decimalFormat.format(price));

            Map<String, Object> item = new HashMap<>();
            item.put("name", room.getRoomType());
            item.put("quantity", bookingDetail.getQuantity());
            item.put("unit_amount", unitAmount);

            items.add(item);
        }
        amount.put("currency_code", "USD");
        amount.put("value", decimalFormat.format(totalPrice));
//        unit.put("items", items);
        unit.put("amount", amount);
        purchaseUnits.add(unit);
        return purchaseUnits;
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
