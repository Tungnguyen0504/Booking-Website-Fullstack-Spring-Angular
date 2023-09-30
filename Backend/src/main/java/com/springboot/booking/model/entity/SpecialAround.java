package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(mappedBy = "specialArounds")
    private Set<Accommodation> accommodations = new HashSet<>();

    public SpecialAround(String description) {
        this.description = description;
    }

    public static SpecialAround create(String description) {
        return new SpecialAround(description);
    }
}
