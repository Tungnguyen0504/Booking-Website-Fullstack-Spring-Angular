package com.springboot.booking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PROPERTY_CONFIG", indexes = @Index(columnList = "PROPERTY"))
public class PropertyConfig extends GenericUUID {
    @Column(name = "PROPERTY")
    private String property;

    @Column(name = "DESCRIPTION")
    private String description;
}
