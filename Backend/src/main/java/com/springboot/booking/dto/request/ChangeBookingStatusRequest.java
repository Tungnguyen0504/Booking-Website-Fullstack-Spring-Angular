package com.springboot.booking.dto.request;

import com.springboot.booking.model.EBookingStatus;
import lombok.Data;

@Data
public class ChangeBookingStatusRequest {
    private long bookingId;
    private EBookingStatus status;
    private String reason;
}
