package vn.spring.booking.dto;

import vn.spring.booking.entities.Accommodation;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.dto.BaseUUIDDto;

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
