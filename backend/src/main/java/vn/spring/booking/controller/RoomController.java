package vn.spring.booking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import vn.spring.booking.common.SuccessResult;
import vn.spring.booking.dto.request.CreateUpdateRoomRequest;
import vn.spring.booking.dto.response.RoomResponse;
import vn.spring.booking.model.BSuccess;
import vn.spring.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static vn.spring.booking.common.Constant.PATH_V1;

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
    public ResponseEntity<RoomResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    @GetMapping("/get-by-accommodation-id/{accommodationId}")
    public ResponseEntity<List<RoomResponse>> getByAccommodationId(@PathVariable Long accommodationId) {
        return ResponseEntity.ok(roomService.getByAccommodationId(accommodationId));
    }
}
