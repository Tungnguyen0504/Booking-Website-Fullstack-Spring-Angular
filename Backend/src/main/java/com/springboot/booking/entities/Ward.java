package com.springboot.booking.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WARD")
public class Ward extends GenericUUID {
    @Column(name = "DISTRICT_ID")
    private UUID accommodationId;

    @Column(name = "WARD_NAME")
    private String wardName;

    @ManyToOne
    @JoinColumn(name = "DISTRICT_ID")
    private District district;

    @OneToMany(mappedBy = "ward")
    private List<Address> addresses;
}
