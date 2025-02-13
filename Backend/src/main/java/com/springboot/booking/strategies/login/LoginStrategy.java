package com.springboot.booking.strategies.login;

import com.springboot.booking.constant.enums.LoginMethod;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.entities.User;
import com.springboot.booking.strategies.LoginStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.library.common.utils.CryptoUtil;

@Component
@Slf4j
public class LoginStrategy extends LoginStrategies {
  public LoginStrategy() {
    this.loginMethod = LoginMethod.EMAIL_PASSWORD;
  }

  @Override
  public boolean execute(User user, LoginRequest request) {
    return CryptoUtil.matchesPassword(
        CryptoUtil.decrypt(request.getPassword()), user.getPassword());
  }
}
