package com.springboot.booking.config;

import com.springboot.booking.dto.response.BookingResponse;
import com.springboot.booking.model.EBookingStatus;
import com.springboot.booking.model.entity.Booking;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.BookingRepository;
import com.springboot.booking.repository.UserRepository;
import com.springboot.booking.service.BookingService;
import com.springboot.booking.service.UserService;
import com.springboot.booking.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final BookingRepository bookingRepository;
    private final WebSocketService socketService;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void scheduled() {
        for (Booking booking : bookingRepository.findBookingAwaitingPaymentExpired()) {
            booking.setStatus(EBookingStatus.PAYMENT_EXPIRED);
            bookingRepository.save(booking);
            String accommodationName = booking.getBookingDetails().get(0).getRoom().getAccommodation().getAccommodationName();
            socketService.push("/notification", "đơn đặt hàng tại " + accommodationName + " đã hết hạn.", "test url", booking.getUser().getId());
        }
        for (Booking booking : bookingRepository.findBookingTimeToFinished()) {
            booking.setStatus(EBookingStatus.FINISHED);
            bookingRepository.save(booking);
            String accommodationName = booking.getBookingDetails().get(0).getRoom().getAccommodation().getAccommodationName();
            socketService.push("/notification", "đơn đặt hàng tại " + accommodationName + " đã hoàn thành.", "test url", booking.getUser().getId());
        }
    }
}
