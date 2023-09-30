package com.springboot.booking.dto.request;

import lombok.Data;

@Data
public class UpdateAccommodationRequest extends AccommodationRequest {
    private String accommodationId;
}
