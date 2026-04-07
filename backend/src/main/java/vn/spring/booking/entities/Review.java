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
@Table(name = "REVIEW")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review extends GenericUUID {
  @Column(name = "BOOKING_ID")
  UUID bookingId;

  @Column(name = "CLEAN")
  Integer clean;

  @Column(name = "AMENITY")
  Integer amenity;

  @Column(name = "SERVICE")
  Integer service;

  @Column(name = "DESCRIPTION")
  String description;

  @Column(name = "ENABLED")
  Boolean enabled;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "BOOKING_ID")
  Booking booking;
}
