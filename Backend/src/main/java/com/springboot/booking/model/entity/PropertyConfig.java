package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property_config", indexes = @Index(columnList = "property"))
@AttributeOverride(name = "id", column = @Column(name = "property_id"))
public class PropertyConfig extends BaseEntity {

    @Column(name = "property")
    private String property;

    @Column(name = "description")
    private String description;
}
