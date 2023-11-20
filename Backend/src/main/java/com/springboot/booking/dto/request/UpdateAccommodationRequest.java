package com.springboot.booking.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAccommodationRequest extends AccommodationRequest {
    private String accommodationId;
}
