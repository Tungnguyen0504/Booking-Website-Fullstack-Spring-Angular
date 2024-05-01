package com.springboot.booking.controller;

import static com.springboot.booking.common.Constant.PATH_AUTH;
import static com.springboot.booking.common.Constant.PATH_V1;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.dto.response.AuthenticationResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(PATH_V1 + PATH_AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/verification/register")
    public ResponseEntity<String> verifyRegister(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.verifyRegister(request));
    }

    @PostMapping("/register")
    public ResponseEntity<BSuccess> register(@RequestBody RegisterRequest request) {
        service.register(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
    }

    @PostMapping("/verification/login")
    public ResponseEntity<String> verifyLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.verifyLogin(request));
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
}
