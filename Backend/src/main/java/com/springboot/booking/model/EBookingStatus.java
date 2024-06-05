package com.springboot.booking.model;

public enum EBookingStatus {
    WAITING_PAYMENT,
    CONFIRMED,
    FINISHED,
    PAYMENT_EXPIRED,
    WAITING_REFUND,
    CANCELED
}
