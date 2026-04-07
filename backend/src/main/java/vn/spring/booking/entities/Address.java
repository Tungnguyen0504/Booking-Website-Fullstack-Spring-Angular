package vn.spring.booking.entities;

import jakarta.persistence.*;
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
@Table(name = "ADDRESS")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends GenericUUID {
  @Column(name = "WARD_ID")
  UUID wardId;

  @Column(name = "SPECIFIC_ADDRESS")
  String specificAddress;

  @ManyToOne
  @JoinColumn(name = "WARD_ID")
  Ward ward;

  @OneToOne(mappedBy = "ADDRESS")
  User user;

  @OneToOne(mappedBy = "address")
  Accommodation accommodation;
}
