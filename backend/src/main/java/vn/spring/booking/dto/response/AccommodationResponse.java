package vn.spring.booking.dto.response;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.dto.BaseUUIDDto;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccommodationResponse extends BaseUUIDDto {
  Long accommodationId;
  String accommodationName;
  AccommodationTypeResponse accommodationType;
  String phone;
  String email;
  Integer star;
  String description;
  Time checkin;
  Time checkout;
  String fullAddress;
  Set<String> specialArounds;
  Set<String> bathRooms;
  Set<String> bedRooms;
  Set<String> dinningRooms;
  Set<String> languages;
  Set<String> internets;
  Set<String> drinkAndFoods;
  Set<String> receptionServices;
  Set<String> cleaningServices;
  Set<String> pools;
  Set<String> others;
  List<String> filePaths;
  List<RoomResponse> rooms;
}
