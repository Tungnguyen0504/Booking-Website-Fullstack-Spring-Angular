package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookingDetailResponse {
    private long id;
    private int quantity;
    private long roomId;
}
