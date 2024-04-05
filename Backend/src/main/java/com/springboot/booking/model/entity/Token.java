package com.springboot.booking.model.entity;

import com.springboot.booking.model.BaseEntity;
import com.springboot.booking.model.ETokenType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token")
@AttributeOverride(name = "id", column = @Column(name = "token_id"))
public class Token extends BaseEntity {

    @Column(name = "token", unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    private ETokenType tokenType = ETokenType.BEARER;

    @Column(name = "revoked")
    private Boolean revoked;

    @Column(name = "expired")
    private Boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
