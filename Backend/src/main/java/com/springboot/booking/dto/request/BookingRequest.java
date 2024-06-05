package com.springboot.booking.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String guestNumber;
    private String note;
    private String estCheckinTime;
    private String paymentMethod;
    private String fromDate;
    private String toDate;
    private String accommodationId;
    private List<BookingDetailRequest> cartItems;
}
