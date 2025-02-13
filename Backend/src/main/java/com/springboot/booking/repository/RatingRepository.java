package com.springboot.booking.repository;

import com.springboot.booking.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByBookingId(Long bookingId);
}
