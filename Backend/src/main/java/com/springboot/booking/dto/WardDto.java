package com.springboot.booking.dto;

import com.springboot.booking.entities.District;
import java.util.List;
import java.util.UUID;
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
public class WardDto extends BaseUUIDDto {
  UUID districtId;
  String wardName;
  District district;
  List<AddressDto> addresses;
}
