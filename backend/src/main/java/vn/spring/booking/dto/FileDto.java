package vn.spring.booking.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.dto.BaseUUIDDto;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileDto extends BaseUUIDDto {
  UUID entityId;
  String entityName;
  String fileName;
  String fileType;
  String filePath;
}
