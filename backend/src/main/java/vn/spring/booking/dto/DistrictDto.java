package vn.spring.booking.dto;

import java.util.List;
import java.util.UUID;

import vn.spring.booking.entities.Province;
import vn.spring.booking.entities.Ward;
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
public class DistrictDto extends BaseUUIDDto {
  UUID provinceId;
  String districtName;
  Province province;
  List<Ward> wards;
}
