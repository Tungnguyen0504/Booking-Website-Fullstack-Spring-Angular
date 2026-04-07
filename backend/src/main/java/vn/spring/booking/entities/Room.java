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
@Table(name = "ROOM")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Room extends GenericUUID {
  @Column(name = "ACCOMMODATION_ID")
  UUID accommodationId;

  @Column(name = "ROOM_TYPE")
  String roomType;

  @Column(name = "ROOM_AREA")
  Double roomArea;

  @Column(name = "BED")
  String bed;

  @Column(name = "CAPACITY")
  Integer capacity;

  @Column(name = "DINNING_ROOM", length = 3000)
  String dinningRoom;

  @Column(name = "BATH_ROOM", length = 3000)
  String bathRoom;

  @Column(name = "ROOM_SERVICE", length = 3000)
  String roomService;

  @Column(name = "SMOKE")
  Boolean smoke;

  @Column(name = "VIEW", length = 500)
  String view;

  @Column(name = "PRICE")
  Double price;

  @Column(name = "DISCOUNT_PERCENT")
  Double discountPercent;

  @Column(name = "QUANTITY")
  Integer quantity;

  @Column(name = "ENABLED")
  Boolean enabled;

  @ManyToOne
  @JoinColumn(name = "ACCOMMODATION_ID")
  Accommodation accommodation;

  @OneToMany(mappedBy = "room")
  List<BookingDetail> bookingDetails;
}
