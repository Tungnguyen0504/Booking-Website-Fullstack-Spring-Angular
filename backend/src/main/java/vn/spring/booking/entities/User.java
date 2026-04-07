package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USER")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends GenericUUID implements UserDetails {
  @Column(name = "ADDRESS_ID")
  UUID addressId;

  @Column(name = "FIRST_NAME")
  String firstName;

  @Column(name = "LAST_NAME")
  String lastName;

  @Column(name = "EMAIL")
  String email;

  @Column(name = "PASSWORD")
  String password;

  @Column(name = "PHONE_NUMBER")
  String phoneNumber;

  @Column(name = "DATE_OF_BIRTH")
  LocalDate dateOfBirth;

  @Column(name = "ENABLED")
  Boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "USER_ROLE",
      joinColumns = @JoinColumn(name = "USER_ID"),
      inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
  List<Role> roles;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ADDRESS_ID")
  Address address;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Booking> bookings;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  List<Notification> notifications;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles.stream()
        .map(role -> new SimpleGrantedAuthority(role.getCode().getCode()))
        .toList();
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
