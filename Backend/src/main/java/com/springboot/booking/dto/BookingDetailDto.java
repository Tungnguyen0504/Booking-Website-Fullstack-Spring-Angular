package com.springboot.booking.dto;

import com.springboot.booking.entities.Booking;
import com.springboot.booking.entities.Room;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.library.common.dto.BaseUUIDDto;

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
