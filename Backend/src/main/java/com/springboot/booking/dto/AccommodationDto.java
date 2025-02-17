package com.springboot.booking.dto;

import com.springboot.booking.constant.enums.StatusCode;
import java.time.LocalTime;
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
public class AccommodationDto extends BaseUUIDDto {
  private UUID addressId;
  private UUID accommodationTypeId;
  private String accommodationName;
  private String phone;
  private String email;
  private Integer star;
  private String description;
  private LocalTime checkin;
  private LocalTime checkout;
  private String specialAround;
  private String bathRoom;
  private String bedRoom;
  private String dinningRoom;
  private String language;
  private String internet;
  private String drinkAndFood;
  private String receptionService;
  private String cleaningService;
  private String pool;
  private String other;
  private StatusCode status;
  private AddressDto address;
  private AccommodationTypeDto accommodationType;
  private List<RoomDto> rooms;
}
