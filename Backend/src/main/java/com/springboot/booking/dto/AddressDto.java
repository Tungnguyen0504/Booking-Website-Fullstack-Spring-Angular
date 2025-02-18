package com.springboot.booking.dto;

import com.springboot.booking.entities.Ward;
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
public class AddressDto extends BaseUUIDDto {
  UUID wardId;
  String specificAddress;
  Ward ward;
}
