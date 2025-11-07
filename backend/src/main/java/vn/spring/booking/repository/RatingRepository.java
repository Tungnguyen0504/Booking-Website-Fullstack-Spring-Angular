package vn.spring.booking.repository;

import vn.spring.booking.entities.Review;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.spring.common.repositories.BaseRepository;

@Repository
public interface RatingRepository extends BaseRepository<Review, UUID> {
  Optional<Review> findByBookingId(UUID bookingId);
}
