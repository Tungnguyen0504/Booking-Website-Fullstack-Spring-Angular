package vn.spring.booking.controller;

import vn.spring.booking.common.SuccessResult;
import vn.spring.booking.dto.request.CreateRatingRequest;
import vn.spring.booking.dto.response.RatingResponse;
import vn.spring.booking.model.BSuccess;
import vn.spring.booking.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static vn.spring.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + "/review")
@RequiredArgsConstructor
public class ReviewController {

    private final RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<BSuccess> create(@RequestBody CreateRatingRequest request) {
        ratingService.create(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }

    @RequestMapping("/get-by-booking-id/{bookingId}")
    public ResponseEntity<RatingResponse> create(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(ratingService.getByBookingId(bookingId));
    }
}
