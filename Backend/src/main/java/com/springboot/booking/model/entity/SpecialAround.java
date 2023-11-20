package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "special_around")
@AttributeOverride(name = "id", column = @Column(name = "special_around_id"))
public class SpecialAround extends BaseEntity {

    @Column(name = "description", unique = true)
    private String description;

    @ManyToMany(mappedBy = "specialArounds")
    private Set<Accommodation> accommodations = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
