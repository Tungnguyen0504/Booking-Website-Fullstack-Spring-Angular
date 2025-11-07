package vn.spring.booking.entities;

import vn.spring.booking.constant.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WARD")
public class Role extends GenericUUID {
  @Column(name = "CODE")
  private UserRole code;
}
