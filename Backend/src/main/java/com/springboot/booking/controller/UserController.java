package com.springboot.booking.controller;

import com.springboot.booking.common.SuccessResult;
import com.springboot.booking.dto.request.ResetPasswordRequest;
import com.springboot.booking.dto.request.CreateUpdateUserRequest;
import com.springboot.booking.dto.request.VerifyEmailRequest;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.model.BSuccess;
import com.springboot.booking.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.booking.common.Constant.PATH_USER;
import static com.springboot.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + PATH_USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-current-user")
    public ResponseEntity<UserResponse> getCurrentUser(@RequestParam String jwt) {
        return ResponseEntity.ok(userService.getCurrentUser(jwt));
    }

    @PutMapping("/update")
    public ResponseEntity<BSuccess> update(@ModelAttribute CreateUpdateUserRequest request) {
        userService.update(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.MODIFIED));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<BSuccess> resetPassword(@RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request);
        return ResponseEntity.ok(new BSuccess(SuccessResult.MODIFIED));
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Map<String, String>> verifyEmail(@RequestBody VerifyEmailRequest request) throws MessagingException {
        return ResponseEntity.ok(userService.verifyEmail(request));
    }
}
