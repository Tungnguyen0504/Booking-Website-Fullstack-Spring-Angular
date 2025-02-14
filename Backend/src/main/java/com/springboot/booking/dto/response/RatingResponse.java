package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RatingResponse {
    private UUID id;
    private int clean;
    private int amenity;
    private int service;
    private String description;
    private String status;
    private UUID bookingId;
}
