package vn.spring.booking.controller;

import vn.spring.booking.common.SuccessResult;
import vn.spring.booking.dto.request.ResetPasswordRequest;
import vn.spring.booking.dto.request.UpdateUserRequest;
import vn.spring.booking.dto.request.VerifyEmailRequest;
import vn.spring.booking.model.BSuccess;
import vn.spring.booking.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

import static vn.spring.booking.common.Constant.PATH_USER;
import static vn.spring.booking.common.Constant.PATH_V1;

@RestController
@RequestMapping(PATH_V1 + PATH_USER)
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @GetMapping("/get-current-user")
  public ResponseEntity<Object> getCurrentUser(Principal principal) {
    return ResponseEntity.ok(userService.getCurrentUser(principal));
  }

  @PutMapping("/update")
  public ResponseEntity<BSuccess> update(
      @ModelAttribute UpdateUserRequest request, Principal principal) throws IOException {
    userService.update(request, principal);
    return ResponseEntity.ok(new BSuccess(SuccessResult.MODIFIED));
  }

  @PutMapping("/reset-password")
  public ResponseEntity<BSuccess> resetPassword(@RequestBody ResetPasswordRequest request) {
    userService.resetPassword(request);
    return ResponseEntity.ok(new BSuccess(SuccessResult.MODIFIED));
  }

  @PostMapping("/verify-email")
  public ResponseEntity<Map<String, String>> verifyEmail(
      @RequestBody VerifyEmailRequest request, Principal principal) throws MessagingException {
    return ResponseEntity.ok(userService.verifyEmail(request, principal));
  }
}
