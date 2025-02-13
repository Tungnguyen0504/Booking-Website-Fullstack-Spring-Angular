package com.springboot.booking.entities;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accommodation")
public class Accommodation extends GenericUUID {
  @Column(name = "accommodation_name")
  private String accommodationName;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "star")
  private Integer star;

  @Column(name = "description", columnDefinition = "TEXT")
  private String description;

  @Column(name = "check_in")
  private LocalTime checkin;

  @Column(name = "checkout")
  private LocalTime checkout;

  @Column(name = "special_around", columnDefinition = "TEXT")
  private String specialAround;

  @Column(name = "bath_room", columnDefinition = "TEXT")
  private String bathRoom;

  @Column(name = "bed_room", columnDefinition = "TEXT")
  private String bedRoom;

  @Column(name = "dinning_room", columnDefinition = "TEXT")
  private String dinningRoom;

  @Column(name = "language")
  private String language;

  @Column(name = "internet")
  private String internet;

  @Column(name = "drink_and_food", columnDefinition = "TEXT")
  private String drinkAndFood;

  @Column(name = "reception_service", columnDefinition = "TEXT")
  private String receptionService;

  @Column(name = "cleaning_service", columnDefinition = "TEXT")
  private String cleaningService;

  @Column(name = "pool", columnDefinition = "TEXT")
  private String pool;

  @Column(name = "other", columnDefinition = "TEXT")
  private String other;

  @Column(name = "status")
  private String status;

  @OneToOne
  @JoinColumn(name = "address_id")
  private Address address;

  @ManyToOne
  @JoinColumn(name = "accommodation_type_id")
  private AccommodationType accommodationType;

  @OneToMany(mappedBy = "accommodation")
  private List<Room> rooms = new ArrayList<>();
}
