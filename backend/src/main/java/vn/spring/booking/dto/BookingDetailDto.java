package vn.spring.booking.dto;

import vn.spring.booking.entities.Booking;
import vn.spring.booking.entities.Room;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.dto.BaseUUIDDto;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDetailDto extends BaseUUIDDto {
  UUID bookingId;
  UUID roomId;
  Integer quantity;
  Room room;
  Booking booking;
}
