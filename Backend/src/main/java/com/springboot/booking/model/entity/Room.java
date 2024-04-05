package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "room")
@AttributeOverride(name = "id", column = @Column(name = "room_id"))
public class Room extends BaseEntity {

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_area")
    private Double roomArea;

    @Column(name = "bed")
    private String bed;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "dinning_room", length = 3000)
    private String dinningRoom;

    @Column(name = "bath_room", length = 3000)
    private String bathRoom;

    @Column(name = "room_service", length = 3000)
    private String roomService;

    @Column(name = "smoke")
    private Boolean smoke;

    @Column(name = "view", length = 500)
    private String view;

    @Column(name = "price")
    private Double price;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @OneToMany(mappedBy = "room")
    private List<BookingDetail> bookingDetails = new ArrayList<>();
}
