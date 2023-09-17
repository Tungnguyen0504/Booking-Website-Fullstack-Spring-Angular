package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "hotel_type")
@AttributeOverride(name = "id", column = @Column(name = "hotel_type_id"))
public class HotelType extends BaseEntity {

    @Column(name = "hotel_type_name", unique = true)
    private String hotelTypeName;

    @OneToMany(mappedBy = "hotelType", cascade = CascadeType.ALL)
    private List<Hotel> hotels = new ArrayList<>();
}
