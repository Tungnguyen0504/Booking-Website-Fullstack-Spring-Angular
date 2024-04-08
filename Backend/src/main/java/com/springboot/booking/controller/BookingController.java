package com.springboot.booking.controller;

import com.paypal.base.rest.PayPalRESTException;
import com.springboot.booking.dto.request.BookingPaymentRequest;
import com.springboot.booking.dto.response.DistrictResponse;
import com.springboot.booking.dto.response.ProvinceResponse;
import com.springboot.booking.dto.response.WardResponse;
import com.springboot.booking.model.entity.Province;
import com.springboot.booking.service.AddressService;
import com.springboot.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

import static com.springboot.booking.common.Constant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/booking")
@RequiredArgsConstructor
public class BookingController {

    private final PaymentService paymentService;

    @PostMapping("/payment/create")
    public RedirectView createPayment(@RequestBody BookingPaymentRequest request) throws PayPalRESTException {
        return new RedirectView(paymentService.createPayment(request));
    }

    @GetMapping("/payment/success")
    public RedirectView paymentSuccess(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        System.out.println("checkpoint");
        return new RedirectView();
//        try {
//            Payment payment = paypalService.executePayment(paymentId, payerId);
//            if (payment.getState().equals("approved")) {
//                return "paymentSuccess";
//            }
//        } catch (PayPalRESTException e) {
//            log.error("Error occurred:: ", e);
//        }
//        return "paymentSuccess";
    }
}
