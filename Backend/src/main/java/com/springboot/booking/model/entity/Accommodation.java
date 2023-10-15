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
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "accommodation")
@AttributeOverride(name = "id", column = @Column(name = "accommodation_id"))
public class Accommodation extends BaseEntity {

    @Column(name = "accommodation_name")
    private String accommodationName;

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

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "accommodation_type_id")
    private AccommodationType accommodationType;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "accommodation_specialaround",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "special_around_id"))
    private Set<SpecialAround> specialArounds = new HashSet<>();
}
