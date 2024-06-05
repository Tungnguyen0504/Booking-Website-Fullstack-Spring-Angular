package com.springboot.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.booking.common.Constant;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Map<String, Object> address;
    private String phoneNumber;
    @JsonFormat(pattern = Constant.DATE_FORMAT1)
    private LocalDate dateOfBirth;
    private String status;
    private String role;
    private List<FileResponse> files;
    @JsonFormat(pattern = Constant.DATETIME_FORMAT2)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = Constant.DATETIME_FORMAT2)
    private LocalDateTime modifiedAt;
}
