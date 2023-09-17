package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "hotel")
@AttributeOverride(name = "id", column = @Column(name = "hotel_id"))
public class Hotel extends BaseEntity {

    @Column(name = "hotel_name")
    private String hotelName;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "star")
    private String star;

    @Column(name = "description")
    private String description;

    @Column(name = "check_in")
    private String checkin;

    @Column(name = "checkout")
    private String checkout;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "hotel_type_id")
    private HotelType hotelType;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<SpecialAround> specialArounds;
}
