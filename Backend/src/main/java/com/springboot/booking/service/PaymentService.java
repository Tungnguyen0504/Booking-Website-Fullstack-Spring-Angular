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

    private JsonObject generateTokenPaypal(BookingPaymentRequest request) throws JsonProcessingException {
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
        return mapper.readValue(response, JsonObject.class);
    }

    public Map<String, Object> createOrder(BookingPaymentRequest request) throws JsonProcessingException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("purchase_units", getPurchaseUnits(request));
        requestBody.put("intent", "CAPTURE");
        requestBody.put("payer", getPayer());
        requestBody.put("application_context", getApplicationContext());
        System.out.println(mapper.writeValueAsString(requestBody));

        WebClient webClient = WebClient.create();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", Util.getBasicAuth(clientId, secret));

        String response = webClient.post()
                .uri("https://api-m.sandbox.paypal.com/v2/checkout/orders")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(BodyInserters.fromValue(mapper.writeValueAsString(requestBody)))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(response);
        return mapper.readValue(response, Map.class);
    }

    private Map<String, Object> getPayer() throws JsonProcessingException {
        UserResponse user = userService.getCurrentUser();
        Map<String, Object> name = new HashMap<>();
        name.put("given_name", user.getFirstName());
        name.put("surname", user.getLastName());

        Map<String, Object> payer = new HashMap<>();
        payer.put("name", name);
        return payer;
    }

    public Payment executePayment(BookingCaptureRequest request) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(request.getPaymentId());
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(request.getPayerId());
        return payment.execute(apiContext, paymentExecution);
    }

    public Map<String, Object> createPayment(BookingPaymentRequest request) throws PayPalRESTException, JsonProcessingException {
        Payment payment = new Payment();
        payment.setTransactions(getTransactionInformation(request));
        payment.setRedirectUrls(getRedirectURLs());
        payment.setPayer(getPayerInformation());
        payment.setIntent("sale");
        Payment paymentCreated = payment.create(apiContext);

        Map<String, Object> response = new HashMap<>();
        response.put("paymentId", paymentCreated.getId());
        response.put("payerId", paymentCreated.getPayer().getPayerInfo().getPayerId());
        System.out.println(response);
        return response;
    }

    private Payer getPayerInformation() {
        UserResponse user = userService.getCurrentUser();

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Nguyen Van");
        payerInfo.setLastName("A");
//        payerInfo.setPhone(user.getPhoneNumber());
        payerInfo.setEmail(user.getEmail());

        Payer payer = new Payer();
        payer.setPaymentMethod("Paypal");
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private Map<String, Object> getApplicationContext() {
        Map<String, Object> applicationContext = new HashMap<>();
        applicationContext.put("return_url", baseUrl + "/booking/success");
        applicationContext.put("cancel_url", baseUrl + "/booking/checkout");
        return applicationContext;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(baseUrl + "/booking/checkout");
        redirectUrls.setReturnUrl(baseUrl + "/booking/success");
        return redirectUrls;
    }

    private List<Map<String, Object>> getPurchaseUnits(BookingPaymentRequest request) throws JsonProcessingException {
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
        unit.put("items", items);
        unit.put("amount", amount);
        purchaseUnits.add(unit);
        return purchaseUnits;
    }

    private List<Transaction> getTransactionInformation(BookingPaymentRequest request) throws JsonProcessingException {
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        Details details = new Details();
        Amount amount = new Amount();
        Transaction transaction = new Transaction();
        ItemList itemList = new ItemList();
        List<Item> items = new ArrayList<>();
        int subDate = DatetimeUtil.subLocalDate(request.getFromDate(), request.getToDate());
        Double totalPrice = 0.0;

        for (BookingDetailRequest bookingDetail : request.getCartItems()) {
            RoomResponse room = roomService.getById(bookingDetail.getRoomId());
            double price = (room.getPrice() * (100 - room.getDiscountPercent()) / 100 * subDate) / RATE_CONVERT;
            price = Double.parseDouble(decimalFormat.format(price)) * bookingDetail.getQuantity();
            totalPrice += price;

            Item item = new Item();
            item.setCurrency("USD");
            item.setName(room.getRoomType() + " x " + bookingDetail.getQuantity());
            item.setPrice(decimalFormat.format(price));
            item.setQuantity(String.valueOf(bookingDetail.getQuantity()));
            items.add(item);
        }
        itemList.setItems(items);

        details.setSubtotal(decimalFormat.format(totalPrice));

        amount.setCurrency("USD");
        amount.setDetails(details);
        amount.setTotal(decimalFormat.format(totalPrice));

        transaction.setAmount(amount);
        transaction.setDescription("Reservation for " + accommodationService.getById(Long.valueOf(request.getAccommodationId())).getAccommodationName());
        transaction.setItemList(itemList);

        return Collections.singletonList(transaction);
    }

    private String getApprovalLink(Payment approvedPayment) {
        return Objects.requireNonNull(Objects.requireNonNull(approvedPayment.getLinks()
                        .stream()
                        .filter(link -> link.getRel().equalsIgnoreCase("approval_url"))
                        .findFirst()
                        .orElse(null))
                .getHref());
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        return Payment.get(apiContext, paymentId);
    }
}
