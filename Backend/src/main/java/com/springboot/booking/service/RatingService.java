package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.dto.request.CreateRatingRequest;
import com.springboot.booking.dto.response.RatingResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.Booking;
import com.springboot.booking.model.entity.Review;
import com.springboot.booking.repository.BookingRepository;
import com.springboot.booking.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final BookingRepository bookingRepository;
    private final RatingRepository ratingRepository;

    public void create(CreateRatingRequest request) {
        Booking booking = bookingRepository.findById(Long.valueOf(request.getBookingId()))
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "đơn đặt hàng"));
        Review review = Review.builder()
                .clean(Integer.valueOf(request.getClean()))
                .amenity(Integer.valueOf(request.getAmenity()))
                .service(Integer.valueOf(request.getService()))
                .description(request.getDescription())
                .status(Constant.STATUS_ACTIVE)
                .booking(booking)
                .build();
        ratingRepository.save(review);
    }

    public RatingResponse getByBookingId(Long bookingId) {
        return ratingRepository.findByBookingId(bookingId)
                .map(this::transferToObject)
                .orElse(null);
    }

    public RatingResponse transferToObject(Review review) {
        return RatingResponse.builder()
                .id(review.getId())
                .clean(review.getClean())
                .amenity(review.getAmenity())
                .service(review.getService())
                .description(review.getDescription())
                .status(review.getStatus())
                .bookingId(review.getBooking().getId())
                .build();
    }
}
