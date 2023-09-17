package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "special_around")
@AttributeOverride(name = "id", column = @Column(name = "special_around_id"))
public class SpecialAround extends BaseEntity {

    @Column(name = "description", unique = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    public SpecialAround(String description) {
        this.description = description;
    }

    public static SpecialAround create(String description) {
        return new SpecialAround(description);
    }
}
