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
@Table(name = "BOOKING_DETAIL")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDetail extends GenericUUID {
  @Column(name = "BOOKING_ID")
  UUID bookingId;

  @Column(name = "ROOM_ID")
  UUID roomId;

  @Column(name = "QUANTITY")
  Integer quantity;

  @ManyToOne
  @JoinColumn(name = "ROOM_ID")
  Room room;

  @ManyToOne
  @JoinColumn(name = "BOOKING_ID")
  Booking booking;
}
