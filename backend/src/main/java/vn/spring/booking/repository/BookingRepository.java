package vn.spring.booking.repository;

import vn.spring.booking.entities.Booking;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface BookingRepository extends BaseRepository<Booking, UUID> {
    @Query(value = "select b from Booking b " +
            "where b.user.id = :userId and b.status = 'WAITING_PAYMENT' " +
            "order by STR_TO_DATE(b.updatedTime, '%Y-%m-%d %H:%i:%s.%f') desc " +
            "limit 1")
    Optional<Booking> findLastBookingAwaitingPayment(UUID userId);

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
