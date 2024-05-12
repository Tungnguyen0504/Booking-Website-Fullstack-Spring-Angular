package com.springboot.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreateUpdateRoomRequest;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + "/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/save")
    public ResponseEntity<BSuccess> createRoom(@ModelAttribute CreateUpdateRoomRequest request) throws JsonProcessingException {
        roomService.createRoom(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping("/get-by-accommodation-id/{accommodationId}")
    public ResponseEntity<List<RoomResponse>> getByAccommodationId(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(roomService.getByAccommodationId(accommodationId));
    }
}
