package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class AccommodationResponse {
    private Long accommodationId;
    private String accommodationName;
    private AccommodationTypeResponse accommodationType;
    private String phone;
    private String email;
    private int star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
    private String fullAddress;
    private List<String> specialArounds;
    private List<String> filePaths;
}
