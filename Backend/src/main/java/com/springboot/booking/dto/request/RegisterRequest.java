package com.springboot.booking.dto.request;

import com.springboot.booking.constant.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String phoneNumber;
    private String password;
    private UserRole userRole;
}