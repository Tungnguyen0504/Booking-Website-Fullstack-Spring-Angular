package com.springboot.booking.dto.request;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class AccommodationRequest {
    private String accommodationName;
    private String address;
    private String phone;
    private String email;
    private String star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
    private long cityId;
    private long accommodationTypeId;
    private List<String> specialArounds;
}
