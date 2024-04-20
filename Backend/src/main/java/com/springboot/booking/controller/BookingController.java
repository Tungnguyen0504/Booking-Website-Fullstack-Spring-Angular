package com.springboot.booking.controller;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.dto.request.BookingCaptureRequest;
import com.springboot.booking.dto.request.BookingPaymentRequest;
import com.springboot.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.Constant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/booking")
@RequiredArgsConstructor
public class BookingController {

    private final PaymentService paymentService;

    @PostMapping("/payment/create")
    public Payment createPayment(@RequestBody BookingPaymentRequest request) throws PayPalRESTException {
        return paymentService.createPayment(request);
    }

    @PostMapping("/payment/success")
    public Payment capturePayment(@RequestBody BookingCaptureRequest request) throws PayPalRESTException {
        return paymentService.executePayment(request);
    }
}
