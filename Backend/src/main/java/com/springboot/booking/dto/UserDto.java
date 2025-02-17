package com.springboot.booking.dto;

import com.springboot.booking.constant.enums.StatusCode;
import com.springboot.booking.entities.Notification;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.library.common.dto.BaseUUIDDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto extends BaseUUIDDto {
  String firstName;
  String lastName;
  String email;
  String password;
  String phoneNumber;
  LocalDate dateOfBirth;
  StatusCode status;
  Set<RoleDto> roles;
  AddressDto address;
  List<BookingDto> bookings;
  List<Notification> notifications;
}
