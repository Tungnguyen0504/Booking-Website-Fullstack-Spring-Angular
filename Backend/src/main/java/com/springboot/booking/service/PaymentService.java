package com.springboot.booking.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.dto.request.BookingDetailRequest;
import com.springboot.booking.dto.request.BookingPaymentRequest;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${environment.local.base-url}")
    private String baseUrl;
    final double RATECONVERT = 23810;

    private final APIContext apiContext;
    private final UserService userService;
    private final AccommodationService accommodationService;
    private final RoomService roomService;

    public Payment createPayment(
            Double total,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total)); // 9.99$ - 9,99€

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }

    public String authorizePayment(BookingPaymentRequest request)
            throws PayPalRESTException {
        Payer payer = getPayerInformation();
        RedirectUrls redirectUrls = getRedirectURLs();
        List<Transaction> listTransaction = getTransactionInformation(request);

        Payment requestPayment = new Payment();
        requestPayment.setTransactions(listTransaction);
        requestPayment.setRedirectUrls(redirectUrls);
        requestPayment.setPayer(payer);
        requestPayment.setIntent("authorize");

        Payment approvedPayment = requestPayment.create(apiContext);
        System.out.println("=== CREATED PAYMENT: ====");
        System.out.println(approvedPayment);
        return getApprovalLink(approvedPayment);
    }

    private Payer getPayerInformation() {
        UserResponse user = userService.getCurrentUser();

        PayerInfo payerInfo = new PayerInfo();
        payerInfo.setFirstName("Nguyen Van");
        payerInfo.setLastName("A");
        payerInfo.setPhone(user.getPhoneNumber());
        payerInfo.setEmail(user.getEmail());

        Payer payer = new Payer();
        payer.setPaymentMethod("Paypal");
        payer.setPayerInfo(payerInfo);
        return payer;
    }

    private RedirectUrls getRedirectURLs() {
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(baseUrl + "/order/checkout");
        redirectUrls.setReturnUrl(baseUrl + "/order/success");
        return redirectUrls;
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

        amount.setCurrency("USD");
        amount.setDetails(details);

        transaction.setAmount(amount);
        transaction.setDescription("Reservation for " + accommodationService.getById(Long.valueOf(request.getAccommodationId())).getAccommodationName());

        for (BookingDetailRequest bookingDetail : request.getBookingDetails()) {
            RoomResponse room = roomService.getById(bookingDetail.getRoomId());
            double price = (room.getPrice() * (100 - room.getDiscountPercent()) / 100 * subDate) / RATECONVERT;
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
        transaction.setItemList(itemList);

        details.setSubtotal(decimalFormat.format(totalPrice));
        amount.setTotal(decimalFormat.format(totalPrice));

        return List.of(transaction);
    }

    private String getApprovalLink(Payment approvedPayment) {
        return approvedPayment.getLinks()
                .stream()
                .filter(link -> link.getRel().equalsIgnoreCase("approval_url"))
                .findFirst()
                .orElse(null)
                .getHref();
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        return Payment.get(apiContext, paymentId);
    }
}
