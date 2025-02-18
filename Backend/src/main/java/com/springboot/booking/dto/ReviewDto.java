package com.springboot.booking.dto;

import com.springboot.booking.constant.enums.StatusCode;
import com.springboot.booking.entities.Booking;
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
public class ReviewDto extends BaseUUIDDto {
  Integer clean;
  Integer amenity;
  Integer service;
  String description;
  StatusCode status;
  Booking booking;
}
