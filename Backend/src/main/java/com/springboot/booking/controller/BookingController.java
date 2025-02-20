package com.springboot.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.common.paging.BasePagingResponse;
import com.springboot.booking.dto.request.BookingCaptureRequest;
import com.springboot.booking.dto.request.BookingRequest;
import com.springboot.booking.dto.request.ChangeBookingStatusRequest;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.BookingService;
import com.springboot.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

import static com.springboot.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + "/booking")
@RequiredArgsConstructor
public class BookingController {

    private final PaymentService paymentService;
    private final BookingService bookingService;

    @PostMapping("/payment/create")
    public ResponseEntity<Map<String, Object>> createPayment(@RequestBody BookingRequest request, Principal principal) throws JsonProcessingException {
        return ResponseEntity.ok(paymentService.createOrder(request, principal));
    }

    @PostMapping("/payment/capture")
    public ResponseEntity<Map<String, Object>> capturePayment(@RequestBody BookingCaptureRequest request) throws JsonProcessingException {
        return ResponseEntity.ok(paymentService.captureOrder(request));
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> create(@RequestBody BookingRequest request, Principal principal) throws JsonProcessingException {
        return ResponseEntity.ok(bookingService.createBookingInfo(request, principal));
    }

    @PostMapping("/get-bookings")
    public ResponseEntity<BasePagingResponse> getBookings(@RequestBody BasePagingRequest request) {
        return ResponseEntity.ok(bookingService.getBookings(request));
    }

    @PutMapping("/change-status")
    public ResponseEntity<BSuccess> changeStatus(@RequestBody ChangeBookingStatusRequest request) {
        bookingService.changeStatus(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.MODIFIED));
    }
}
