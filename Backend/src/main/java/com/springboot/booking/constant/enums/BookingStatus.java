package com.springboot.booking.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum BookingStatus {
    WAITING_PAYMENT,
    CONFIRMED,
    FINISHED,
    PAYMENT_EXPIRED,
    WAITING_REFUND,
    CANCELED
}
