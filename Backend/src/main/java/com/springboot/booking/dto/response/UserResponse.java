package com.springboot.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.booking.common.Constant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Map<String, Object> address;
    private String phoneNumber;
    @JsonFormat(pattern = Constant.DATE_FORMAT1)
    private LocalDate dateOfBirth;
    private String status;
    private String role;
    private String filePath;
    @JsonFormat(pattern = Constant.DATETIME_FORMAT2)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = Constant.DATETIME_FORMAT2)
    private LocalDateTime modifiedAt;
}
