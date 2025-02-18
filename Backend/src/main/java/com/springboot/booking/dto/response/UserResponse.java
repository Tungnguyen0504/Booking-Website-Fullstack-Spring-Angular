package com.springboot.booking.dto.response;

import com.springboot.booking.constant.enums.StatusCode;
import com.springboot.booking.entities.Role;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import vn.library.common.dto.BaseUUIDDto;

@Getter
@Setter
@Builder
@ToString
public class UserResponse extends BaseUUIDDto {
  private String firstName;
  private String lastName;
  private String email;
  private AddressResponse address;
  private String phoneNumber;
  private LocalDate dateOfBirth;
  private StatusCode status;
  private List<Role> roles;
  private FileResponse file;
}
