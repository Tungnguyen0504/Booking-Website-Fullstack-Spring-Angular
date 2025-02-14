package com.springboot.booking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address extends GenericUUID {
  @Column(name = "WARD_ID")
  private UUID wardId;

  @Column(name = "SPECIFIC_ADDRESS")
  private String specificAddress;

  @ManyToOne
  @JoinColumn(name = "WARD_ID")
  private Ward ward;

  @OneToOne(mappedBy = "ADDRESS")
  private User user;

  @OneToOne(mappedBy = "address")
  private Accommodation accommodation;
}
