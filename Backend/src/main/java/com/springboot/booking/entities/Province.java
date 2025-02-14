package com.springboot.booking.entities;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROVINCE")
public class Province extends GenericUUID {
  @Column(name = "PROVINCE_NAME")
  private String provinceName;

  @OneToMany(mappedBy = "province")
  private List<District> districts;
}
