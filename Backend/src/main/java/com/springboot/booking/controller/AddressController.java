package com.springboot.booking.controller;

import com.springboot.booking.dto.response.DistrictResponse;
import com.springboot.booking.dto.response.ProvinceResponse;
import com.springboot.booking.dto.response.WardResponse;
import com.springboot.booking.model.entity.District;
import com.springboot.booking.model.entity.Province;
import com.springboot.booking.model.entity.Ward;
import com.springboot.booking.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.booking.common.AbstractConstant.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_USER)
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
    public ResponseEntity<List<DistrictResponse>> getDistrictsByProvince(@RequestParam Long provinceId) {
        return ResponseEntity.ok(addressService.getDistrictsByProvince(provinceId));
    }

    @GetMapping("/get-wards-by-district")
    public ResponseEntity<List<WardResponse>> getWardsByDistrict(@RequestParam Long districtId) {
        return ResponseEntity.ok(addressService.getWardsByDistrict(districtId));
    }
}
