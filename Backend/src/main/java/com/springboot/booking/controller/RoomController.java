package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreateRoomRequest;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.AbstractConstant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/save")
    public ResponseEntity<BSuccess> createRoom(@ModelAttribute CreateRoomRequest request) {
        roomService.createRoom(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }
}
