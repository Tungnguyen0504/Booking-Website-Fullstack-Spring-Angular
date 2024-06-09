package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RatingResponse {
    private long id;
    private int clean;
    private int amenity;
    private int service;
    private String description;
    private String status;
    private long bookingId;
}
