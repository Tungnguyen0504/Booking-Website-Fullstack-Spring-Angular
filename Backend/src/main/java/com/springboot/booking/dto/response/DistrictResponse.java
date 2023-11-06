package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DistrictResponse {

    private Long districtId;
    private String districtName;
}
