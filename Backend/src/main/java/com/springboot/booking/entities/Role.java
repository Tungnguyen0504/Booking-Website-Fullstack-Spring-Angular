package com.springboot.booking.entities;

import com.springboot.booking.constant.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

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
