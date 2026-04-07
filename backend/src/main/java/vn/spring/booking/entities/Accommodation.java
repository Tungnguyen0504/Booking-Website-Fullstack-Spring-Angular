package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOMMODATION")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Accommodation extends GenericUUID {
  @Column(name = "ADDRESS_ID")
  UUID addressId;

  @Column(name = "ACCOMMODATION_TYPE_ID")
  UUID accommodationTypeId;

  @Column(name = "ACCOMMODATION_NAME")
  String accommodationName;

  @Column(name = "PHONE")
  String phone;

  @Column(name = "EMAIL")
  String email;

  @Column(name = "STAR")
  Integer star;

  @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
  String description;

  @Column(name = "CHECKIN")
  LocalTime checkin;

  @Column(name = "CHECKOUT")
  LocalTime checkout;

  @Column(name = "SPECIAL_AROUND")
  String specialAround;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "NEARBY_ATTRACTIONS")
  List<String> nearbyAttractions;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "BATH_ROOM")
  List<String> bathRooms;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "BED_ROOM")
  List<String> bedRooms;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "DINNING_ROOM")
  List<String> dinningRooms;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "LANGUAGE")
  List<String> languages;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "INTERNET")
  List<String> internets;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "DRINK_AND_FOOD")
  List<String> drinkAndFoods;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "RECEPTION_SERVICE")
  List<String> receptionServices;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "CLEANING_SERVICE")
  List<String> cleaningServices;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "POOL")
  List<String> pools;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "OTHER")
  List<String> others;

  @Column(name = "ENABLED")
  Boolean enabled;

  @OneToOne
  @JoinColumn(name = "ADDRESS_ID")
  Address address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ACCOMMODATION_TYPE_ID")
  AccommodationType accommodationType;

  @OneToMany(mappedBy = "accommodation")
  List<Room> rooms;
}
