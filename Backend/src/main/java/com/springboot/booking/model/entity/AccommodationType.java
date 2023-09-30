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
@Table(name = "accommodation_type")
@AttributeOverride(name = "id", column = @Column(name = "accommodation_type_id"))
public class AccommodationType extends BaseEntity {

    @Column(name = "accommodation_type_name", unique = true)
    private String accommodationTypeName;

    @OneToMany(mappedBy = "accommodationType", cascade = CascadeType.ALL)
    private List<Accommodation> accommodations = new ArrayList<>();
}
