package com.springboot.booking.controller;

import static com.springboot.booking.common.Constant.PATH_V1;

import com.springboot.booking.dto.response.DistrictResponse;
import com.springboot.booking.dto.response.ProvinceResponse;
import com.springboot.booking.dto.response.WardResponse;
import com.springboot.booking.entities.Province;
import com.springboot.booking.service.AddressService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PATH_V1 + "/address")
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  @GetMapping("/get-top-province")
  public ResponseEntity<List<Province>> getListProvince(@RequestParam int range) {
    return ResponseEntity.ok(addressService.getTopProvince(range));
  }

  @GetMapping("/get-all-province")
  public ResponseEntity<List<ProvinceResponse>> getAllProvince() {
    return ResponseEntity.ok(addressService.getAllProvince());
  }

  @GetMapping("/get-districts-by-province")
  public ResponseEntity<List<DistrictResponse>> getDistrictsByProvince(
      @RequestParam UUID provinceId) {
    return ResponseEntity.ok(addressService.getDistrictsByProvince(provinceId));
  }

  @GetMapping("/get-wards-by-district")
  public ResponseEntity<List<WardResponse>> getWardsByDistrict(@RequestParam UUID districtId) {
    return ResponseEntity.ok(addressService.getWardsByDistrict(districtId));
  }

  @GetMapping("/get-full-address")
  public ResponseEntity<String> getFullAddress(@RequestParam UUID addressId) {
    return ResponseEntity.ok(addressService.getFullAddress(addressId));
  }
}
