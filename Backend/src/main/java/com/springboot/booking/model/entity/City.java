package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "city")
@AttributeOverride(name = "id", column = @Column(name = "city_id"))
public class City extends BaseEntity {
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;
}
