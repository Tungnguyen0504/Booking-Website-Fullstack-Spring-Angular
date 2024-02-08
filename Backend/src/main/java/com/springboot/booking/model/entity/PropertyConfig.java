package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "property_config")
@AttributeOverride(name = "id", column = @Column(name = "property_id"))
public class PropertyConfig extends BaseEntity {

    @Column(name = "property")
    private String property;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyConfig propertyConfig = (PropertyConfig) o;
        return Objects.equals(property, propertyConfig.getProperty()) &&
                Objects.equals(description, propertyConfig.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(property, description);
    }
}
