package com.springboot.booking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FILE")
public class File extends GenericUUID {
    @Column(name = "ENTITY_ID")
    private String entityId;

    @Column(name = "ENTITY_NAME")
    private String entityName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Column(name = "FILE_PATH")
    private String filePath;
}
