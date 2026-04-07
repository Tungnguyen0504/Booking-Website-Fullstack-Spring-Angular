package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.util.List;
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
@Table(name = "DISTRICT")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class District extends GenericUUID {
  @Column(name = "PROVINCE_ID")
  UUID provinceId;

  @Column(name = "DISTRICT_NAME")
  String districtName;

  @ManyToOne
  @JoinColumn(name = "PROVINCE_ID")
  Province province;

  @OneToMany(mappedBy = "district")
  List<Ward> wards;
}
