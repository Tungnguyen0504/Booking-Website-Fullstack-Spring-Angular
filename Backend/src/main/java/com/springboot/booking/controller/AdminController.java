package com.springboot.booking.controller;

import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.Constant.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_ADMIN)
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

//    @GetMapping
//    @PreAuthorize("hasAuthority('admin:read')")
//    public String get() {
//        return "GET:: admin controller";
//    }
//
//    @PostMapping
//    @PreAuthorize("hasAuthority('admin:create')")
//    @Hidden
//    public String post() {
//        return "POST:: admin controller";
//    }
//
//    @PutMapping
//    @PreAuthorize("hasAuthority('admin:update')")
//    @Hidden
//    public String put() {
//        return "PUT:: admin controller";
//    }
//
//    @DeleteMapping
//    @PreAuthorize("hasAuthority('admin:delete')")
//    @Hidden
//    public String delete() {
//        return "DELETE:: admin controller";
//    }

//    @PostMapping("/save-image-file")
//    public ResponseEntity<BSuccess> getListCity(@RequestParam("image") MultipartFile file) {
//        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
//    }
}
