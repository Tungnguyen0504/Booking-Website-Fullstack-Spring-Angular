package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.CreatePropertyConfigRequest;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.PropertyConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.springboot.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + "/property-config")
@RequiredArgsConstructor
public class PropertyConfigController {

    private final PropertyConfigService propertyConfigService;

    @PostMapping("/create")
    public ResponseEntity<BSuccess> create(@RequestBody CreatePropertyConfigRequest request) {
        propertyConfigService.create(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }

    @GetMapping("/get-by-property")
    public ResponseEntity<List<String>> getByProperty(@RequestParam String property) {
        return ResponseEntity.ok(propertyConfigService.getByProperty(property));
    }
}
