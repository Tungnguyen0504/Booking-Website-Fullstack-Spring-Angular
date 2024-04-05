package com.springboot.booking.dto.request;

import lombok.Data;

@Data
public class BookingDetailRequest {
    private Integer quantity;
    private Long roomId;
}
