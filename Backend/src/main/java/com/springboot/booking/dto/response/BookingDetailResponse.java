package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BookingDetailResponse {
    private UUID id;
    private int quantity;
    private UUID roomId;
}
