package com.springboot.booking.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CreateAccommodationRequest {
    private String accommodationName;
    private long accommodationTypeId;
    private String phone;
    private String email;
    private int star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
    private long wardId;
    private String specificAddress;
    private Set<String> specialArounds;
    private List<MultipartFile> files;
}
