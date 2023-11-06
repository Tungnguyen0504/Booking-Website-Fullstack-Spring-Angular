package com.springboot.booking.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "province")
@AttributeOverride(name = "id", column = @Column(name = "province_id"))
public class Province extends BaseEntity {

    @Column(name = "province_name")
    private String provinceName;

    @OneToMany(mappedBy = "province")
    private List<District> districts;
}