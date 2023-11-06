package com.springboot.booking.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "district")
@AttributeOverride(name = "id", column = @Column(name = "district_id"))
public class District extends BaseEntity {

    @Column(name = "district_name")
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @OneToMany(mappedBy = "district")
    private List<Ward> wards;
}
