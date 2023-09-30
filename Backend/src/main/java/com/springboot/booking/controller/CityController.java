package com.springboot.booking.controller;

import com.springboot.booking.model.entity.City;
import com.springboot.booking.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.booking.common.AbstractConstant.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_USER)
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @GetMapping("/get-top-city")
    public ResponseEntity<List<City>> getListCity(@RequestParam int range) {
        return ResponseEntity.ok(cityService.getTopCity(range));
    }
}
