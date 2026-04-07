package vn.spring.booking.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.spring.booking.constant.enums.BookingStatus;
import vn.spring.common.entities.GenericUUID;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOKING")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Booking extends GenericUUID {
  @Column(name = "USER_ID")
  UUID userId;

  @Column(name = "FIRST_NAME")
  String firstName;

  @Column(name = "LAST_NAME")
  String lastName;

  @Column(name = "EMAIL")
  String email;

  @Column(name = "PHONE_NUMBER")
  String phoneNumber;

  @Column(name = "GUEST_NUMBER")
  Integer guestNumber;

  @Column(name = "NOTE")
  String note;

  @Column(name = "EST_CHECKIN_TIME")
  String estCheckinTime;

  @Column(name = "PAYMENT_METHOD")
  String paymentMethod;

  @Column(name = "TOTAL_AMOUNT")
  Double totalAmount;

  @Column(name = "FROM_DATE")
  LocalDate fromDate;

  @Column(name = "TO_DATE")
  LocalDate toDate;

  @Enumerated(EnumType.STRING)
  @Column(name = "STATUS")
  BookingStatus status;

  @Column(name = "REASON")
  String reason;

  @ManyToOne
  @JoinColumn(name = "USER_ID")
  User user;

  @OneToOne(mappedBy = "booking")
  Review review;

  @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
  List<BookingDetail> bookingDetails;
}
