package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private int star;

    @Column(name = "description", length = 10000)
    private String description;

    @Column(name = "check_in")
    private LocalTime checkin;

    @Column(name = "checkout")
    private LocalTime checkout;

    @Column(name = "status")
    private String status;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "accommodation_type_id")
    private AccommodationType accommodationType;

    @ManyToMany
    @JoinTable(name = "accommodation_specialaround",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "special_around_id"))
    private Set<SpecialAround> specialArounds = new HashSet<>();

    @OneToMany(mappedBy = "accommodation")
    private List<Room> rooms = new ArrayList<>();
}
