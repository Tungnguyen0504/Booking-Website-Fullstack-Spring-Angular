package com.springboot.booking.controller;

import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.service.JwtService;
import com.springboot.booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import static com.springboot.booking.common.Constant.PATH_USER;
import static com.springboot.booking.common.Constant.PATH_V1;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(PATH_V1 + PATH_USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationFacade authenticationFacade;

    @GetMapping("/get-current-user")
    public ResponseEntity<UserResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/check-token")
    public ResponseEntity<Boolean> isTokenValid(@RequestParam String token) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(jwtService.isTokenValid(token, userDetails));
        }
        return ResponseEntity.ok(false);
    }
}
