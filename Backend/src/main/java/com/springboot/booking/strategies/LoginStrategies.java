package com.springboot.booking.strategies;

import com.springboot.booking.constant.enums.LoginMethod;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.entities.User;
import lombok.Getter;

@Getter
public abstract class LoginStrategies {
  protected LoginMethod loginMethod;

  public abstract boolean execute(User user, LoginRequest request);
}
