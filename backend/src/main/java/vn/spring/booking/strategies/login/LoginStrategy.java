package vn.spring.booking.strategies.login;

import vn.spring.booking.constant.enums.LoginMethod;
import vn.spring.booking.dto.request.LoginRequest;
import vn.spring.booking.entities.User;
import vn.spring.booking.strategies.LoginStrategies;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.spring.common.utils.CryptoUtil;

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
