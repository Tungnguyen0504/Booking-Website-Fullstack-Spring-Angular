package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.AbstractConstant.PATH_USER;
import static com.springboot.booking.common.AbstractConstant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_USER + "/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping
    public ResponseEntity<BSuccess> createAccommodation(@RequestBody CreateAccommodationRequest request) {
        accommodationService.createAccommodation(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }
}
