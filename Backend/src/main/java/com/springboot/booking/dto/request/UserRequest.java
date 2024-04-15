package com.springboot.booking.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String wardId;
    private String specificAddress;
    private String phoneNumber;
    private String dateOfBirth;
    private MultipartFile file;
}
