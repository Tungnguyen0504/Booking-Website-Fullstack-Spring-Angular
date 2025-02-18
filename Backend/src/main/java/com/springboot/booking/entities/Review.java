package com.springboot.booking.entities;

import com.springboot.booking.constant.enums.StatusCode;
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
@Table(name = "REVIEW")
public class Review extends GenericUUID {
    @Column(name = "BOOKING_ID")
    private UUID bookingId;

    @Column(name = "CLEAN")
    private Integer clean;

    @Column(name = "AMENITY")
    private Integer amenity; //tiện ích

    @Column(name = "SERVICE")
    private Integer service;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private StatusCode status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOKING_ID")
    private Booking booking;
}
