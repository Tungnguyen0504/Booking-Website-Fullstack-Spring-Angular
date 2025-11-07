package vn.spring.booking.service;

import vn.spring.booking.common.Constant;
import vn.spring.booking.common.ExceptionResult;
import vn.spring.booking.dto.request.CreateRatingRequest;
import vn.spring.booking.dto.response.RatingResponse;
import vn.spring.booking.entities.Booking;
import vn.spring.booking.entities.Review;
import vn.spring.booking.exeption.GlobalException;
import vn.spring.booking.repository.BookingRepository;
import vn.spring.booking.repository.RatingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final BookingRepository bookingRepository;
    private final RatingRepository ratingRepository;

    public void create(CreateRatingRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
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

    public RatingResponse getByBookingId(UUID bookingId) {
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
