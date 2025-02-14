package com.springboot.booking.dto.request;

import com.springboot.booking.constant.enums.BookingStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class ChangeBookingStatusRequest {
    private UUID bookingId;
    private BookingStatus status;
    private String reason;
}
