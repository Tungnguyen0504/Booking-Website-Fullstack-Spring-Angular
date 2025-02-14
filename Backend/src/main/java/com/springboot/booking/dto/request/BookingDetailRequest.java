package com.springboot.booking.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class BookingDetailRequest {
    private Integer quantity;
    private UUID roomId;
}
