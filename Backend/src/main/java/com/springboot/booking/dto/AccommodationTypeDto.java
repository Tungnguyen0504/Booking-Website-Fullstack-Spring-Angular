package com.springboot.booking.dto;

import com.springboot.booking.entities.Accommodation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.library.common.dto.BaseUUIDDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccommodationTypeDto extends BaseUUIDDto {
  String accommodationTypeName;
  String status;
  List<Accommodation> accommodations;
}
