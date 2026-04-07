package vn.spring.booking.dto;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.booking.entities.AccommodationType;
import vn.spring.booking.entities.Address;
import vn.spring.booking.entities.Room;
import vn.spring.common.dto.BaseUUIDEnabledDto;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccommodationDto extends BaseUUIDEnabledDto {
  UUID addressId;
  UUID accommodationTypeId;
  String accommodationName;
  String phone;
  String email;
  Integer star;
  String description;
  LocalTime checkin;
  LocalTime checkout;
  String specialAround;
  List<String> nearbyAttractions;
  List<String> bathRooms;
  List<String> bedRooms;
  List<String> dinningRooms;
  List<String> languages;
  List<String> internets;
  List<String> drinkAndFoods;
  List<String> receptionServices;
  List<String> cleaningServices;
  List<String> pools;
  List<String> others;
  Address address;
  AccommodationType accommodationType;
  List<Room> rooms;
}
