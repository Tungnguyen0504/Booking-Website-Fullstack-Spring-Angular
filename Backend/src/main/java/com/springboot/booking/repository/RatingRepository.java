package com.springboot.booking.repository;

import com.springboot.booking.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByBookingId(Long bookingId);
}
