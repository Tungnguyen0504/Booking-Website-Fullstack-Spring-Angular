package com.springboot.booking.entities;

import com.springboot.booking.constant.enums.StatusCode;
import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import vn.library.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ACCOMMODATION")
public class Accommodation extends GenericUUID {
  @Column(name = "ADDRESS_ID")
  private UUID addressId;
  
  @Column(name = "ACCOMMODATION_TYPE_ID")
  private UUID accommodationTypeId;
  
  @Column(name = "ACCOMMODATION_NAME")
  private String accommodationName;

  @Column(name = "PHONE")
  private String phone;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "STAR")
  private Integer star;

  @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
  private String description;

  @Column(name = "CHECKIN")
  private LocalTime checkin;

  @Column(name = "CHECKOUT")
  private LocalTime checkout;

  @Column(name = "SPECIAL_AROUND", columnDefinition = "TEXT")
  private String specialAround;

  @Column(name = "BATH_ROOM", columnDefinition = "TEXT")
  private String bathRoom;

  @Column(name = "BED_ROOM", columnDefinition = "TEXT")
  private String bedRoom;

  @Column(name = "DINNING_ROOM", columnDefinition = "TEXT")
  private String dinningRoom;

  @Column(name = "LANGUAGE")
  private String language;

  @Column(name = "INTERNET")
  private String internet;

  @Column(name = "DRINK_AND_FOOD", columnDefinition = "TEXT")
  private String drinkAndFood;

  @Column(name = "RECEPTION_SERVICE", columnDefinition = "TEXT")
  private String receptionService;

  @Column(name = "CLEANING_SERVICE", columnDefinition = "TEXT")
  private String cleaningService;

  @Column(name = "POOL", columnDefinition = "TEXT")
  private String pool;

  @Column(name = "OTHER", columnDefinition = "TEXT")
  private String other;

  @Column(name = "STATUS")
  @ColumnDefault("ACTIVE")
  @Enumerated(EnumType.STRING)
  private StatusCode status;

  @OneToOne
  @JoinColumn(name = "ADDRESS_ID")
  private Address address;

  @ManyToOne
  @JoinColumn(name = "ACCOMMODATION_TYPE_ID")
  private AccommodationType accommodationType;

  @OneToMany(mappedBy = "accommodation")
  private List<Room> rooms;
}
