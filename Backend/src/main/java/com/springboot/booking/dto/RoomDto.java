package com.springboot.booking.dto;

import com.springboot.booking.constant.enums.StatusCode;
import com.springboot.booking.entities.Accommodation;
import com.springboot.booking.entities.BookingDetail;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.library.common.dto.BaseUUIDDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoomDto extends BaseUUIDDto {
  UUID accommodationId;
  String roomType;
  Double roomArea;
  String bed;
  Integer capacity;
  String dinningRoom;
  String bathRoom;
  String roomService;
  Boolean smoke;
  String view;
  Double price;
  Double discountPercent;
  Integer quantity;
  StatusCode status;
  AccommodationDto accommodation;
  List<BookingDetailDto> bookingDetails;
}
