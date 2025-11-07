package vn.spring.booking.controller;

import vn.spring.booking.common.SuccessResult;
import vn.spring.booking.dto.request.CreatePropertyConfigRequest;
import vn.spring.booking.model.BSuccess;
import vn.spring.booking.service.PropertyConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static vn.spring.booking.common.Constant.PATH_V1;

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
