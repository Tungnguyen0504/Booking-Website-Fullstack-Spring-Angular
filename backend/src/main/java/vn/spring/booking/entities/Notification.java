package vn.spring.booking.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "NOTIFICATION")
public class Notification extends GenericUUID {
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "URL")
    private String url;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}