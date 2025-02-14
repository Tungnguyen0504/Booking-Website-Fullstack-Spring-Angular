package com.springboot.booking.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
public class CreateUpdateUserRequest {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String dateOfBirth;
    private String wardId;
    private String specificAddress;
    private List<MultipartFile> files;
}
