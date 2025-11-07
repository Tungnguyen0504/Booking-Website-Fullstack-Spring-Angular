package vn.spring.booking.controller;

import static vn.spring.booking.common.Constant.PATH_AUTH;
import static vn.spring.booking.common.Constant.PATH_V1;

import vn.spring.booking.common.SuccessResult;
import vn.spring.booking.dto.request.LoginRequest;
import vn.spring.booking.dto.request.RegisterRequest;
import vn.spring.booking.model.BSuccess;
import vn.spring.booking.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping(PATH_V1 + PATH_AUTH)
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthService authService;

  @PostMapping("/register")
  public ResponseEntity<BSuccess> register(
      @RequestBody RegisterRequest request, Principal principal) {
    authService.register(request, principal);
    return ResponseEntity.ok(new BSuccess(SuccessResult.CREATED));
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.login(loginRequest));
  }

  @PostMapping("/logout")
  public ResponseEntity<BSuccess> login(Principal principal) {
    authService.logout(principal);
    return ResponseEntity.ok(new BSuccess(SuccessResult.LOGOUT_SUCCESS));
  }
}
