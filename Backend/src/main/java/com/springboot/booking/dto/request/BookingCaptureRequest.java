package com.springboot.booking.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingCaptureRequest {
    private String paymentId;
    private String payerId;
}
