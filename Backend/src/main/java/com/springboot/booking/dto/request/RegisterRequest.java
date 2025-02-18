package com.springboot.booking.dto.request;

import com.springboot.booking.constant.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
  String email;
  String phoneNumber;
  String password;
  UserRole role;
}
