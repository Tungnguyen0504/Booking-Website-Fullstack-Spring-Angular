package vn.spring.booking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FILE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class File extends GenericUUID {
  @Column(name = "ENTITY_ID")
  UUID entityId;

  @Column(name = "ENTITY_NAME")
  String entityName;

  @Column(name = "FILE_NAME")
  String fileName;

  @Column(name = "FILE_TYPE")
  String fileType;

  @Column(name = "FILE_PATH")
  String filePath;
}
