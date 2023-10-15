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
@Table(name = "address")
@AttributeOverride(name = "id", column = @Column(name = "address_id"))
public class Address extends BaseEntity {

    @Column(name = "specific_address", unique = true)
    private String specificAddress;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Ward ward;

    @OneToOne(mappedBy = "address")
    private Accommodation accommodation;
}
