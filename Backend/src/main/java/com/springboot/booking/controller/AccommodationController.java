package com.springboot.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.dto.response.AccommodationResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.AccommodationService;
import com.springboot.booking.service.FileService;
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
    private final FileService fileService;

    @PostMapping("/save")
    public ResponseEntity<BSuccess> createAccommodation(@ModelAttribute CreateAccommodationRequest request) throws JsonProcessingException {
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
}
