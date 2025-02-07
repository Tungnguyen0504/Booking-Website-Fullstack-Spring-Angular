package com.springboot.booking.controller;

import com.springboot.booking.dto.response.AccommodationTypeResponse;
import com.springboot.booking.service.AccommodationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springboot.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + "/accommodation-type")
@RequiredArgsConstructor
public class AccommodationTypeController {

    private final AccommodationTypeService accommodationTypeService;

    @GetMapping("/get-all-accommodation-type")
    public ResponseEntity<List<AccommodationTypeResponse>> getAllAccommodationType() {
        return ResponseEntity.ok(accommodationTypeService.getAllAccommodationType());
    }
}
