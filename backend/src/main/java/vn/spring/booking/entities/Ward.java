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
@Table(name = "WARD")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ward extends GenericUUID {
  @Column(name = "DISTRICT_ID")
  UUID districtId;

  @Column(name = "WARD_NAME")
  String wardName;

  @ManyToOne
  @JoinColumn(name = "DISTRICT_ID")
  District district;

  @OneToMany(mappedBy = "ward")
  List<Address> addresses;
}
