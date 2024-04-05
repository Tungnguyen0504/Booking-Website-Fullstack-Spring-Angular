package com.springboot.booking.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingPaymentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String guestNumber;
    private String note;
    private String estCheckinTime;
    private String paymentMethod;
    private String totalAmount;
    private String fromDate;
    private String toDate;
    private String accommodationId;
    private List<BookingDetailRequest> bookingDetails;
}
