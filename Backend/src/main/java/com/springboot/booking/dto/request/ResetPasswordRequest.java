package com.springboot.booking.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ResetPasswordRequest {
    private UUID id;
    private String password;
}
