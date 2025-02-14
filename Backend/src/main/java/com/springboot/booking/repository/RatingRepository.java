package com.springboot.booking.repository;

import com.springboot.booking.entities.Review;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;
import vn.library.common.repositories.BaseRepository;

@Repository
public interface RatingRepository extends BaseRepository<Review, UUID> {
  Optional<Review> findByBookingId(UUID bookingId);
}
