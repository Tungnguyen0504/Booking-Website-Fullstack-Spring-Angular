package com.springboot.booking.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum LoginMethod {
  EMAIL_PASSWORD("EMAIL_PASSWORD", "Login with email and password"),
  GOOGLE("GOOGLE", "Login with google");

  private static final Map<String, LoginMethod> dataMap = new HashMap<>();

  static {
    for (LoginMethod c : LoginMethod.values()) {
      if (dataMap.put(c.getMethod(), c) != null) {
        throw new IllegalArgumentException("error code: " + c.getMethod());
      }
    }
  }

  private final String method;
  private final String description;

  public static LoginMethod of(String method) {
    return dataMap.get(method);
  }
}
