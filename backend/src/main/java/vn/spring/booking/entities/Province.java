package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.util.List;
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
@Table(name = "PROVINCE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Province extends GenericUUID {
  @Column(name = "PROVINCE_NAME")
  String provinceName;

  @OneToMany(mappedBy = "province")
  List<District> districts;
}
