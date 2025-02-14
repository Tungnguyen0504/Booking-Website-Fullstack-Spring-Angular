package com.springboot.booking.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "DISTRICT")
public class District extends GenericUUID {
  @Column(name = "PROVINCE_ID")
  private UUID provinceId;

  @Column(name = "DISTRICT_NAME")
  private String districtName;

  @ManyToOne
  @JoinColumn(name = "PROVINCE_ID")
  private Province province;

  @OneToMany(mappedBy = "district")
  private List<Ward> wards;
}
