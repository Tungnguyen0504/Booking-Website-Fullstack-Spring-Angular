package com.springboot.booking.controller;

import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.dto.request.SendVerificationCodeRequest;
import com.springboot.booking.dto.response.AuthenticationResponse;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.springboot.booking.common.AbstractConstant.PATH_AUTH;
import static com.springboot.booking.common.AbstractConstant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/send-verification-code")
    public void sendVerificationCode(@RequestBody SendVerificationCodeRequest request) {
        service.sendVerificationCode(request);
    }

    @PostMapping("/register")
    public void register(@RequestBody RegisterRequest request) {
        service.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @GetMapping("/get-current-user")
    public ResponseEntity<User> getCurrentUser() {
        return ResponseEntity.ok(service.getCurrentUser());
    }
}
