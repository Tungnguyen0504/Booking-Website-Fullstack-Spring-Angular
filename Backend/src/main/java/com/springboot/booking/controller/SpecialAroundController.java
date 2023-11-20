package com.springboot.booking.controller;

import com.springboot.booking.dto.response.SpecialAroundResponse;
import com.springboot.booking.service.SpecialAroundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.springboot.booking.common.AbstractConstant.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/special-around")
@RequiredArgsConstructor
public class SpecialAroundController {

    private final SpecialAroundService specialAroundService;

    @GetMapping("/get-all-special-around")
    public ResponseEntity<List<SpecialAroundResponse>> getAllSpecialAround() {
        return ResponseEntity.ok(specialAroundService.getAllSpecialAround());
    }
}
