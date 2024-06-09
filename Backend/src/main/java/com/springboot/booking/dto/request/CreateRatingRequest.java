package com.springboot.booking.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateRatingRequest {
    private String clean;
    private String amenity;
    private String service;
    private String description;
    private String bookingId;
}
