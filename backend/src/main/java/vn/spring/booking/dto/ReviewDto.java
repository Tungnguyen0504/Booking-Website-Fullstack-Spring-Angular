package vn.spring.booking.dto;

import vn.spring.booking.constant.enums.StatusCode;
import vn.spring.booking.entities.Booking;
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
public class ReviewDto extends BaseUUIDDto {
  Integer clean;
  Integer amenity;
  Integer service;
  String description;
  StatusCode status;
  Booking booking;
}
