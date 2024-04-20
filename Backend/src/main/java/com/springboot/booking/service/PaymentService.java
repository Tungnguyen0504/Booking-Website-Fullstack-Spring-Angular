package com.springboot.booking.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.dto.request.BookingCaptureRequest;
import com.springboot.booking.dto.request.BookingDetailRequest;
import com.springboot.booking.dto.request.BookingPaymentRequest;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {
    @Value("${environment.local.base-url}")
    private String baseUrl;
    final double RATE_CONVERT = 23810;

    private final APIContext apiContext;
    private final UserService userService;
    private final AccommodationService accommodationService;
    private final RoomService roomService;

    public Payment executePayment(BookingCaptureRequest request) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(request.getPaymentId());
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(request.getPayerId());
        return payment.execute(apiContext, paymentExecution);
    }

    public Payment createPayment(BookingPaymentRequest request) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setTransactions(getTransactionInformation(request));
        payment.setRedirectUrls(getRedirectURLs());
        payment.setPayer(getPayerInformation());
        payment.setIntent("sale");
        return payment.create(apiContext);
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
