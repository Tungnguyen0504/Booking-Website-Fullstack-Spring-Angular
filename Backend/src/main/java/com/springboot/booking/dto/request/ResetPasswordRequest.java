package com.springboot.booking.dto.request;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private Long id;
    private String password;
}
