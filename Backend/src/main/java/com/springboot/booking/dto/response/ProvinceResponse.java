package com.springboot.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProvinceResponse {

    private Long provinceId;
    private String provinceName;
}
