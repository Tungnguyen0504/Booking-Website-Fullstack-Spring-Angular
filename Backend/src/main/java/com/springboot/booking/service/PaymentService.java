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

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.details;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${environment.local.base-url}")
    private String baseUrl;
    final double RATE_CONVERT = 23810;
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

    public JsonObject createOrder(BookingPaymentRequest request) {
        return null;
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

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(baseUrl + "/booking/checkout");
        redirectUrls.setReturnUrl(baseUrl + "/booking/success");
        return redirectUrls;
    }

    private JsonArray getPurchaseUnits(BookingPaymentRequest request) {
        JsonArray purchaseUnits = new JsonArray();
        JsonObject unit = new JsonObject();
        JsonObject amount = new JsonObject();
        JsonArray items = new JsonArray();

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        int subDate = DatetimeUtil.subLocalDate(request.getFromDate(), request.getToDate());
        Double totalPrice = 0.0;

        for (BookingDetailRequest bookingDetail : request.getCartItems()) {
            RoomResponse room = roomService.getById(bookingDetail.getRoomId());
            double price = (room.getPrice() * (100 - room.getDiscountPercent()) / 100 * subDate) / RATE_CONVERT;
            price = Double.parseDouble(decimalFormat.format(price)) * bookingDetail.getQuantity();
            totalPrice += price;

            JsonObject unitAmount = new JsonObject();
            unitAmount.addProperty("currency_code", "USD");
            unitAmount.addProperty("value", decimalFormat.format(price));

            JsonObject item = new JsonObject();
            item.addProperty("name", room.getRoomType());
            item.addProperty("quantity", bookingDetail.getQuantity());
            item.addProperty("unit_amount", String.valueOf(unitAmount));

            items.add(item);
        }
        amount.addProperty("currency_code", "USD");
        amount.addProperty("value", decimalFormat.format(totalPrice));
        unit.addProperty("items", String.valueOf(items));
        unit.addProperty("amount", String.valueOf(amount));
        purchaseUnits.add(unit);
        return purchaseUnits;
    }

    private List<Transaction> getTransactionInformation(BookingPaymentRequest request) {
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
