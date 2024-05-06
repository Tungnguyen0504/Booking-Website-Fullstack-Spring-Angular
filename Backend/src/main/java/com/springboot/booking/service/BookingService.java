package com.springboot.booking.service;

import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.dto.request.BookingRequest;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.EBookingStatus;
import com.springboot.booking.model.entity.Booking;
import com.springboot.booking.model.entity.BookingDetail;
import com.springboot.booking.repository.BookingDetailRepository;
import com.springboot.booking.repository.BookingRepository;
import com.springboot.booking.repository.RoomRepository;
import com.springboot.booking.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    @Value("${currency.rate.usd}")
    private String currencyRate;

    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    private final RoomService roomService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final BookingDetailRepository bookingDetailRepository;

    @Transactional
    public void createBookingInfo(BookingRequest request) {
        UserResponse user = userService.getCurrentUser();

        Booking booking = Booking.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .guestNumber(Integer.valueOf(request.getGuestNumber()))
                .note(request.getNote())
                .estCheckinTime(request.getEstCheckinTime())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(calculateTotalAmount(request))
                .fromDate(DatetimeUtil.parseDateDefault(request.getFromDate()))
                .toDate(DatetimeUtil.parseDateDefault(request.getToDate()))
                .status(EBookingStatus.CONFIRMED)
                .user(userRepository.findByEmail(user.getEmail())
                        .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "người dùng")))
                .build();

        List<BookingDetail> bookingDetails = request.getCartItems()
                .stream()
                .map(cart -> BookingDetail.builder()
                        .quantity(cart.getQuantity())
                        .room(roomRepository.findById(cart.getRoomId())
                                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "phòng")))
                        .booking(booking)
                        .build())
                .toList();

        bookingDetailRepository.saveAll(bookingDetails);
        bookingRepository.save(booking);
    }

    public double calculateTotalAmount(BookingRequest request) {
        int subDate = DatetimeUtil.subLocalDate(request.getFromDate(), request.getToDate());
        return request.getCartItems()
                .stream()
                .map(req -> {
                    RoomResponse room = roomService.getById(req.getRoomId());
                    double subPrice = ((room.getPrice() * ((100 - room.getDiscountPercent()) / 100) * req.getQuantity() * subDate) / Double.parseDouble(currencyRate));
                    return Double.parseDouble(decimalFormat.format(subPrice));
                })
                .reduce(0.0, Double::sum);
    }
}
