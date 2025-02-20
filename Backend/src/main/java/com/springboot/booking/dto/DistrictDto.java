package com.springboot.booking.dto;

import java.util.List;
import java.util.UUID;

import com.springboot.booking.entities.Province;
import com.springboot.booking.entities.Ward;
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
public class DistrictDto extends BaseUUIDDto {
  UUID provinceId;
  String districtName;
  Province province;
  List<Ward> wards;
}
