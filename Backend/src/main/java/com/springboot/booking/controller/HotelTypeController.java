package com.springboot.booking.controller;

import com.springboot.booking.model.entity.HotelType;
import com.springboot.booking.service.HotelTypeSerive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springboot.booking.common.AbstractConstant.PATH_USER;
import static com.springboot.booking.common.AbstractConstant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_USER)
@RequiredArgsConstructor
public class HotelTypeController {
    private final HotelTypeSerive hotelTypeSerive;

    @GetMapping("/get-all-hotel-type")
    public ResponseEntity<List<HotelType>> getAllHotelType() {
        return ResponseEntity.ok(hotelTypeSerive.getAllHotelType());
    }
}
