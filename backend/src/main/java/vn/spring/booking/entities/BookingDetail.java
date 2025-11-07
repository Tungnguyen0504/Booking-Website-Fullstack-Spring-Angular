package vn.spring.booking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKING_DETAIL")
public class BookingDetail extends GenericUUID {
  @Column(name = "BOOKING_ID")
  private UUID bookingId;

  @Column(name = "ROOM_ID")
  private UUID roomId;

  @Column(name = "QUANTITY")
  private Integer quantity;

  @ManyToOne
  @JoinColumn(name = "ROOM_ID")
  private Room room;

  @ManyToOne
  @JoinColumn(name = "BOOKING_ID")
  private Booking booking;
}
