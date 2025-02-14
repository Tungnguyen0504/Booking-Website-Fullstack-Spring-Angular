package com.springboot.booking.entities;

import com.springboot.booking.model.BaseEntity;
import com.springboot.booking.constant.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.library.common.entities.GenericUUID;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "BOOKING")
public class Booking extends GenericUUID {
    @Column(name = "USER_ID")
    private UUID userId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "GUEST_NUMBER")
    private Integer guestNumber;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "EST_CHECKIN_TIME")
    private String estCheckinTime;

    @Column(name = "PAYMENT_METHOD")
    private String paymentMethod;

    @Column(name = "TOTAL_AMOUNT")
    private Double totalAmount;

    @Column(name = "FROM_DATE")
    private LocalDate fromDate;

    @Column(name = "TO_DATE")
    private LocalDate toDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private BookingStatus status;

    @Column(name = "REASON")
    private String reason;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(mappedBy = "booking")
    private Review review;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<BookingDetail> bookingDetails;
}
