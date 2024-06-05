package com.springboot.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.booking.common.Constant;
import com.springboot.booking.model.EBookingStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BookingResponse {
    private long id;
    private long accommodationId;
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
    private EBookingStatus status;
    private long userId;
    private List<BookingDetailResponse> bookingDetails;
}
