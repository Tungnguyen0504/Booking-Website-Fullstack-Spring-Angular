package vn.spring.booking.controller;

import static vn.spring.booking.common.Constant.PATH_V1;

import vn.spring.booking.common.SuccessResult;
import vn.spring.booking.common.paging.BasePagingResponse;
import vn.spring.booking.dto.request.CreateAccommodationRequest;
import vn.spring.booking.dto.request.SearchAccommodationRequest;
import vn.spring.booking.dto.response.AccommodationResponse;
import vn.spring.booking.model.BSuccess;
import vn.spring.booking.service.AccommodationService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + "/accommodation")
@RequiredArgsConstructor
public class AccommodationController {

  private final AccommodationService accommodationService;

  @PostMapping("/save")
  public ResponseEntity<BSuccess> createAccommodation(
      @ModelAttribute CreateAccommodationRequest request) throws Exception {
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
  public ResponseEntity<BasePagingResponse> pagingAccommodation(
      @RequestBody SearchAccommodationRequest request) {
    return ResponseEntity.ok(accommodationService.getAccommodations(request));
  }

  @PutMapping("/inactive/{id}")
  public ResponseEntity<BSuccess> inactive(@PathVariable UUID id) {
    accommodationService.inactive(id);
    return ResponseEntity.ok(new BSuccess(SuccessResult.DELETED));
  }
}
