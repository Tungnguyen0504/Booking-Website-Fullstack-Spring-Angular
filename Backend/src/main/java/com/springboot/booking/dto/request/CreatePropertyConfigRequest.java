package com.springboot.booking.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CreatePropertyConfigRequest {
    private String property;
    private Set<String> descriptions;
}
