package com.springboot.booking.entities;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "province")
@AttributeOverride(name = "id", column = @Column(name = "province_id"))
public class Province extends BaseEntity {

    @Column(name = "province_name")
    private String provinceName;

    @OneToMany(mappedBy = "province")
    private List<District> districts;
}