package vn.spring.booking.dto;

import vn.spring.booking.constant.enums.StatusCode;
import vn.spring.booking.entities.Notification;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.dto.BaseUUIDDto;

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
  List<RoleDto> roles;
  AddressDto address;
  List<BookingDto> bookings;
  List<Notification> notifications;
}
