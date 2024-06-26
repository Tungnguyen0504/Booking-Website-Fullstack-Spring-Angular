package com.springboot.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.common.paging.BasePagingResponse;
import com.springboot.booking.dto.request.CreateUpdateAccommodationRequest;
import com.springboot.booking.dto.request.SearchAccommodationRequest;
import com.springboot.booking.dto.response.AccommodationResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.AccommodationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.booking.common.Constant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

    private final AccommodationService accommodationService;

    @PostMapping("/save")
    public ResponseEntity<BSuccess> createAccommodation(@ModelAttribute CreateUpdateAccommodationRequest request) throws JsonProcessingException {
        accommodationService.createAccommodation(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<AccommodationResponse>> getAll() {
        return ResponseEntity.ok(accommodationService.getAllAccommodationActive());
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<AccommodationResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(accommodationService.getById(id));
    }

    @PostMapping("/get-accommodations")
    public ResponseEntity<BasePagingResponse> pagingAccommodation(@RequestBody SearchAccommodationRequest request) {
        return ResponseEntity.ok(accommodationService.getAccommodations(request));
    }

    @PutMapping("/inactive/{id}")
    public ResponseEntity<BSuccess> inactive(@PathVariable Long id) {
        accommodationService.inactive(id);
        return ResponseEntity.ok(new BSuccess(SuccessResult.DELETED));
    }
}
