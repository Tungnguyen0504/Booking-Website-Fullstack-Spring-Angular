package com.springboot.booking.dto.request;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class AccommodationRequest {
    private String accommodationName;
    private String phone;
    private String email;
    private String star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
    private long accommodationTypeId;
    private long wardId;
    private String specificAddress;
    private List<String> specialArounds;
}
