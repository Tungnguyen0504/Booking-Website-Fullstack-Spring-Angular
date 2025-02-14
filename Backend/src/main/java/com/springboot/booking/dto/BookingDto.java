package com.springboot.booking.dto;

import com.springboot.booking.constant.enums.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.library.common.dto.BaseUUIDDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDto extends BaseUUIDDto {
  String firstName;
  String lastName;
  String email;
  String phoneNumber;
  Integer guestNumber;
  String note;
  String estCheckinTime;
  String paymentMethod;
  Double totalAmount;
  LocalDate fromDate;
  LocalDate toDate;
  BookingStatus status;
  String reason;
  ReviewDto review;
  List<BookingDetailDto> bookingDetails;
}
