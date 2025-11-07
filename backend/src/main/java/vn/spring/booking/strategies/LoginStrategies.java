package vn.spring.booking.strategies;

import vn.spring.booking.constant.enums.LoginMethod;
import vn.spring.booking.dto.request.LoginRequest;
import vn.spring.booking.entities.User;
import lombok.Getter;

@Getter
public abstract class LoginStrategies {
  protected LoginMethod loginMethod;

  public abstract boolean execute(User user, LoginRequest request);
}
