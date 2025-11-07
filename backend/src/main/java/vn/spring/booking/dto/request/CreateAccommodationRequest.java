package vn.spring.booking.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccommodationRequest {
  String accommodationName;
  UUID accommodationTypeId;
  String phone;
  String email;
  String star;
  String description;
  LocalTime checkin;
  LocalTime checkout;
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
  String wardId;
  String specificAddress;
  List<MultipartFile> files;
}
