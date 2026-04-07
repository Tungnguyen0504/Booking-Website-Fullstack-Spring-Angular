package vn.spring.booking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "ACCOMMODATION_TYPE")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccommodationType extends GenericUUID {
  @Column(name = "ACCOMMODATION_TYPE_NAME")
  String accommodationTypeName;

  @Column(name = "ENABLED")
  Boolean enabled;

  @OneToMany(mappedBy = "accommodationType")
  List<Accommodation> accommodations;
}
