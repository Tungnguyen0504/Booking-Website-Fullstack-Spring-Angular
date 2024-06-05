package com.springboot.booking.repository;

import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {
    @Query(value = "select b from Booking b " +
            "where b.user.id = :userId and b.status = 'WAITING_PAYMENT' " +
            "order by STR_TO_DATE(b.modifiedAt, '%Y-%m-%d %H:%i:%s.%f') desc " +
            "limit 1")
    Optional<Booking> findLastBookingAwaitingPayment(Long userId);

    @Query(value = "select b.* \n" +
            "from Booking b \n" +
            "         join user u \n" +
            "              on b.user_id = u.user_id \n" +
            "         join booking_detail bd \n" +
            "              on b.booking_id = bd.booking_id \n" +
            "         join room r \n" +
            "              on bd.room_id = r.room_id \n" +
            "         join accommodation a \n" +
            "              on r.accommodation_id = a.accommodation_id \n" +
            "where DATEDIFF(b.to_date, now()) <= -2 \n" +
            "  and b.status = 'WAITING_PAYMENT' ", nativeQuery = true)
    List<Booking> findBookingAwaitingPaymentExpired();

    @Query(value = "select b.* \n" +
            "from Booking b \n" +
            "         join user u \n" +
            "              on b.user_id = u.user_id \n" +
            "         join booking_detail bd \n" +
            "              on b.booking_id = bd.booking_id \n" +
            "         join room r \n" +
            "              on bd.room_id = r.room_id \n" +
            "         join accommodation a \n" +
            "              on r.accommodation_id = a.accommodation_id \n" +
            "where DATEDIFF(b.to_date, now()) <= 0 \n" +
            "  and b.status = 'CONFIRMED' ", nativeQuery = true)
    List<Booking> findBookingTimeToFinished();
}
