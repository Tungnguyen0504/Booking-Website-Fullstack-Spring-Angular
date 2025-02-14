package com.springboot.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.booking.common.Constant;
import com.springboot.booking.constant.enums.BookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BookingResponse {
    private UUID id;
    private UUID accommodationId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int guestNumber;
    private String note;
    private String estCheckinTime;
    private String paymentMethod;
    private double totalAmount;
    @JsonFormat(pattern = Constant.DATE_FORMAT1)
    private LocalDate fromDate;
    @JsonFormat(pattern = Constant.DATE_FORMAT1)
    private LocalDate toDate;
    private BookingStatus status;
    private UUID userId;
    private List<BookingDetailResponse> bookingDetails;
}
