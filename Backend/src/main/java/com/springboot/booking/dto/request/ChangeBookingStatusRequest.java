package com.springboot.booking.dto.request;

import com.springboot.booking.constant.enums.BookingStatus;
import lombok.Data;

@Data
public class ChangeBookingStatusRequest {
    private long bookingId;
    private BookingStatus status;
    private String reason;
}
