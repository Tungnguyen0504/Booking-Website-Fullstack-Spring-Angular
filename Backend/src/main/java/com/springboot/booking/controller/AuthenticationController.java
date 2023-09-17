package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.dto.request.VerificationRequest;
import com.springboot.booking.dto.response.AuthenticationResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.model.entity.City;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.AbstractConstant.PATH_AUTH;
import static com.springboot.booking.common.AbstractConstant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/verification")
    public ResponseEntity<String> verification(@RequestBody VerificationRequest request) {
        return ResponseEntity.ok(service.verification(request));
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(service.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<BSuccess> login(@RequestBody String jwt) {
        service.logout(jwt);
        return ResponseEntity.ok(new BSuccess(SuccessResult.LOGOUT_SUCCESS));
    }

    @GetMapping("/get-current-user")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }
}
