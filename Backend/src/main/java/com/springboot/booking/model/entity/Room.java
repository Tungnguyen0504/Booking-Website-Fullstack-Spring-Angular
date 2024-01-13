package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
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
    private double roomArea;

    @Column(name = "bed")
    private String bed;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "dinning_room", length = 3000)
    private String dinningRoom;

    @Column(name = "bath_room", length = 3000)
    private String bathRoom;

    @Column(name = "room_service", length = 3000)
    private String roomService;

    @Column(name = "smoke")
    private boolean smoke;

    @Column(name = "view", length = 500)
    private String view;

    @Column(name = "price")
    private double price;

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;
}
