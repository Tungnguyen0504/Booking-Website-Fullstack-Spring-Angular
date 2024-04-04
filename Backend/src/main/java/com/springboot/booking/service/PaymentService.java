package com.springboot.booking.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final APIContext apiContext;
    private final UserService userService;

//    public Payment createPayment(
//            Double total,
//            String currency,
//            String method,
//            String intent,
//            String description,
//            String cancelUrl,
//            String successUrl
//    ) throws PayPalRESTException {
//        Amount amount = new Amount();
//        amount.setCurrency(currency);
//        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total)); // 9.99$ - 9,99€
//
//        Transaction transaction = new Transaction();
//        transaction.setDescription(description);
//        transaction.setAmount(amount);
//
//        List<Transaction> transactions = new ArrayList<>();
//        transactions.add(transaction);
//
//        Payer payer = new Payer();
//        payer.setPaymentMethod(method);
//
//        Payment payment = new Payment();
//        payment.setIntent(intent);
//        payment.setPayer(payer);
//        payment.setTransactions(transactions);
//
//        RedirectUrls redirectUrls = new RedirectUrls();
//        redirectUrls.setCancelUrl(cancelUrl);
//        redirectUrls.setReturnUrl(successUrl);
//
//        payment.setRedirectUrls(redirectUrls);
//
//        return payment.create(apiContext);
//    }
//
//    public Payment executePayment(
//            String paymentId,
//            String payerId
//    ) throws PayPalRESTException {
//        Payment payment = new Payment();
//        payment.setId(paymentId);
//
//        PaymentExecution paymentExecution = new PaymentExecution();
//        paymentExecution.setPayerId(payerId);
//
//        return payment.execute(apiContext, paymentExecution);
//    }
//
//    public String authorizePayment()
//            throws PayPalRESTException {
//        Payer payer = getPayerInformation(request, booking);
//        RedirectUrls redirectUrls = getRedirectURLs(request, bookingDetails);
//        List<Transaction> listTransaction = getTransactionInformation(booking, bookingDetails, request);
//
//        Payment requestPayment = new Payment();
//        requestPayment.setTransactions(listTransaction);
//        requestPayment.setRedirectUrls(redirectUrls);
//        requestPayment.setPayer(payer);
//        requestPayment.setIntent("authorize");
//
//        Payment approvedPayment = requestPayment.create(apiContext);
//        System.out.println("=== CREATED PAYMENT: ====");
//        System.out.println(approvedPayment);
//        return getApprovalLink(approvedPayment);
//    }
//
//    private Payer getPayerInformation() {
//        UserResponse user = userService.getCurrentUser();
//
//        PayerInfo payerInfo = new PayerInfo();
//        payerInfo.setFirstName("Nguyen Van");
//        payerInfo.setLastName("A");
//        payerInfo.setPhone(user.getPhoneNumber());
//        payerInfo.setEmail(user.getEmail());
//
//        Payer payer = new Payer();
//        payer.setPaymentMethod("Paypal");
//        payer.setPayerInfo(payerInfo);
//        return payer;
//    }
//
//    private RedirectUrls getRedirectURLs() {
//        RedirectUrls redirectUrls = new RedirectUrls();
//        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//        try {
//            redirectUrls.setCancelUrl(url + "/checkout?hotelId=" + roomDAO.getRoomByRoomId(bookingDetails.get(0).getRoomId()).getHotelId());
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        redirectUrls.setReturnUrl(url + "/confirm");
//
//        return redirectUrls;
//    }
//
//    private List<Transaction> getTransactionInformation(Booking booking, List<BookingDetail> bookingDetails, HttpServletRequest request) {
//        try {
//            double rateConvert = 23810;
//
//            DecimalFormat format = new DecimalFormat("#0.00");
//            Details details = new Details();
//
//            Amount amount = new Amount();
//            amount.setCurrency("USD");
//            amount.setDetails(details);
//
//            Transaction transaction = new Transaction();
//            transaction.setAmount(amount);
//            transaction.setDescription("Reservation for " + hotelDAO.getHotelById(roomDAO.getRoomByRoomId(bookingDetails.get(0).getRoomId()).getHotelId()).getHotelName());
//
//            ItemList itemList = new ItemList();
//            List<Item> items = new ArrayList<>();
//
//            long subDate = (long) request.getSession().getAttribute("subDate");
//
//            double totalPrice = 0;
//            for (BookingDetail detail : bookingDetails) {
//                Room room = roomDAO.getRoomByRoomId(detail.getRoomId());
//                double price = (room.getPrice() * (100 - room.getDiscountPercent()) / 100 * subDate) / rateConvert;
//
//                Item item = new Item();
//                item.setCurrency("USD");
//                item.setName(room.getRoomType() + " x " + detail.getQuantityRoom());
//                item.setPrice(format.format(price));
//                item.setQuantity(detail.getQuantityRoom() + "");
//
//                items.add(item);
//                price = Double.parseDouble(format.format(price)) * detail.getQuantityRoom();
//                totalPrice += price;
//            }
//
//            itemList.setItems(items);
//            transaction.setItemList(itemList);
//
//            details.setSubtotal(format.format(totalPrice));
//            amount.setTotal(format.format(totalPrice));
//
//            List<Transaction> listTransaction = new ArrayList<>();
//            listTransaction.add(transaction);
//
//            return listTransaction;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private String getApprovalLink(Payment approvedPayment) {
//        List<Links> links = approvedPayment.getLinks();
//        String approvalLink = null;
//
//        for (Links link : links) {
//            if (link.getRel().equalsIgnoreCase("approval_url")) {
//                approvalLink = link.getHref();
//                break;
//            }
//        }
//
//        return approvalLink;
//    }
//
//    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
//        return Payment.get(apiContext, paymentId);
//    }
}
