package com.springboot.booking.config;

import com.springboot.booking.constant.enums.BookingStatus;
import com.springboot.booking.entities.Booking;
import com.springboot.booking.repository.BookingRepository;
import com.springboot.booking.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ScheduledTask {
    private final BookingRepository bookingRepository;
    private final WebSocketService socketService;

    @Scheduled(fixedRate = 60 * 1000)
    @Transactional
    public void scheduled() {
        for (Booking booking : bookingRepository.findBookingAwaitingPaymentExpired()) {
            booking.setStatus(BookingStatus.PAYMENT_EXPIRED);
            bookingRepository.save(booking);
            String accommodationName = booking.getBookingDetails().get(0).getRoom().getAccommodation().getAccommodationName();
            socketService.push("/notification", "đơn đặt hàng tại " + accommodationName + " đã hết hạn.", "test url", booking.getUser().getId());
        }
        for (Booking booking : bookingRepository.findBookingTimeToFinished()) {
            booking.setStatus(BookingStatus.FINISHED);
            bookingRepository.save(booking);
            String accommodationName = booking.getBookingDetails().get(0).getRoom().getAccommodation().getAccommodationName();
            socketService.push("/notification", "đơn đặt hàng tại " + accommodationName + " đã hoàn thành.", "test url", booking.getUser().getId());
        }
    }
}
