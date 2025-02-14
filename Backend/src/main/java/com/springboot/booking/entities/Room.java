package com.springboot.booking.entities;

import com.springboot.booking.constant.enums.Status;
import jakarta.persistence.*;
import java.util.ArrayList;
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
@Table(name = "ROOM")
public class Room extends GenericUUID {
    @Column(name = "ACCOMMODATION_ID")
    private UUID accommodationId;

    @Column(name = "ROOM_TYPE")
    private String roomType;

    @Column(name = "ROOM_AREA")
    private Double roomArea;

    @Column(name = "BED")
    private String bed;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "DINNING_ROOM", length = 3000)
    private String dinningRoom;

    @Column(name = "BATH_ROOM", length = 3000)
    private String bathRoom;

    @Column(name = "ROOM_SERVICE", length = 3000)
    private String roomService;

    @Column(name = "SMOKE")
    private Boolean smoke;

    @Column(name = "VIEW", length = 500)
    private String view;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "DISCOUNT_PERCENT")
    private Double discountPercent;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "STATUS")
    @ColumnDefault("ACTIVE")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "ACCOMMODATION_ID")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room")
    private List<BookingDetail> bookingDetails;
}
