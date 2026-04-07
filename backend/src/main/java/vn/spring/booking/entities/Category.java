package vn.spring.booking.entities;

import jakarta.persistence.*;
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
@Table(name = "CATEGORY")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends GenericUUID {
  @Column(name = "CODE")
  String code;

  @Column(name = "NAME")
  String name;

  @Column(name = "DESCRIPTION")
  String description;

  @Column(name = "TYPE_CODE")
  String typeCode;

  @Column(name = "TYPE_ID")
  UUID typeId;

  @Column(name = "ENABLED")
  Boolean enabled;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "TYPE_ID")
  CategoryType categoryType;
}
