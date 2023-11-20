package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.springboot.booking.common.AbstractConstant.PATH_USER;
import static com.springboot.booking.common.AbstractConstant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("/save")
    public ResponseEntity<BSuccess> createAccommodation(@ModelAttribute CreateAccommodationRequest request) {
        accommodationService.createAccommodation(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }
}
