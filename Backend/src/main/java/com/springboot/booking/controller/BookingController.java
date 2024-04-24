package com.springboot.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.dto.request.BookingCaptureRequest;
import com.springboot.booking.dto.request.BookingPaymentRequest;
import com.springboot.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.booking.common.Constant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/booking")
@RequiredArgsConstructor
public class BookingController {

    private final PaymentService paymentService;

    @PostMapping("/payment/create")
    public ResponseEntity<Map<String, Object>> createPayment(@RequestBody BookingPaymentRequest request) throws PayPalRESTException, JsonProcessingException {
        return ResponseEntity.ok(paymentService.createOrder(request));
    }

    @PostMapping("/payment/success")
    public ResponseEntity<Payment> capturePayment(@RequestBody BookingCaptureRequest request) throws PayPalRESTException {
        return ResponseEntity.ok(paymentService.executePayment(request));
    }
}
