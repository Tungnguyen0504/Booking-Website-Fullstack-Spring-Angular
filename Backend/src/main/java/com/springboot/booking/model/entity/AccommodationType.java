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
@Table(name = "accommodation_type")
@AttributeOverride(name = "id", column = @Column(name = "accommodation_type_id"))
public class AccommodationType extends BaseEntity {

    @Column(name = "accommodation_type_name", unique = true)
    private String accommodationTypeName;

    @Column(name = "status")
    private String status;

    @OneToMany(mappedBy = "accommodationType")
    private List<Accommodation> accommodations = new ArrayList<>();
}
