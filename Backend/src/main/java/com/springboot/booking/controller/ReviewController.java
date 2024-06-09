package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreateRatingRequest;
import com.springboot.booking.dto.response.RatingResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.Constant.PATH_V1;

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
    public ResponseEntity<RatingResponse> create(@PathVariable Long bookingId) {
        return ResponseEntity.ok(ratingService.getByBookingId(bookingId));
    }
}
