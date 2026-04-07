package vn.spring.booking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.booking.constant.enums.UserRole;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "WARD")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role extends GenericUUID {
  @Column(name = "CODE")
  UserRole code;
}
