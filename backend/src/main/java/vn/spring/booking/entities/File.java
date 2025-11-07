package vn.spring.booking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILE")
public class File extends GenericUUID {
  @Column(name = "ENTITY_ID")
  private UUID entityId;

  @Column(name = "ENTITY_NAME")
  private String entityName;

  @Column(name = "FILE_NAME")
  private String fileName;

  @Column(name = "FILE_TYPE")
  private String fileType;

  @Column(name = "FILE_PATH")
  private String filePath;
}
