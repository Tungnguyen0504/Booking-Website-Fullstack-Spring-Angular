package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "NOTIFICATION")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends GenericUUID {
  @Column(name = "USER_ID")
  UUID userId;

  @Column(name = "MESSAGE")
  String message;

  @Column(name = "URL")
  String url;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  User user;
}
