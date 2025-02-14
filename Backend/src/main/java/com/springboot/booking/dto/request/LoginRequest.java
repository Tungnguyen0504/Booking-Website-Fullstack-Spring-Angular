package com.springboot.booking.dto.request;

import com.springboot.booking.constant.enums.LoginMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.library.common.annotation.SensitiveData;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginRequest {
  @Schema(description = "Email Đăng nhập")
  @NotBlank(message = "validation.username.not.blank")
  String email;

  @Schema(description = "Password Đăng nhập")
  @NotBlank(message = "validation.password.not.blank")
  @SensitiveData
  String password;

  @Schema(description = "Phương thức Đăng nhập")
  @NotBlank(message = "validation.login.method.not.blank")
  LoginMethod method;
}
