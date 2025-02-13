package com.springboot.booking.entities;

import com.springboot.booking.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "notification")
@AttributeOverride(name = "id", column = @Column(name = "notification_id"))
@ToString
public class Notification extends BaseEntity {

    @Column(name = "message")
    private String message;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}