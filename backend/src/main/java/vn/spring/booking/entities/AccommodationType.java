package vn.spring.booking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOMMODATION_TYPE")
public class AccommodationType extends GenericUUID {
  @Column(name = "ACCOMMODATION_TYPE_NAME", unique = true)
  private String accommodationTypeName;

  @Column(name = "STATUS")
  private String status;

  @OneToMany(mappedBy = "accommodationType")
  private List<Accommodation> accommodations;
}
