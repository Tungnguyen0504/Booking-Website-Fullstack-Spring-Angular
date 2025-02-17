package com.springboot.booking.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum StatusCode {
  ACTIVE("ACTIVE"),
  INACTIVE("INACTIVE");

  private static final Map<String, StatusCode> dataMap = new HashMap<>();

  static {
    for (StatusCode c : StatusCode.values()) {
      if (dataMap.put(c.getCode(), c) != null) {
        throw new IllegalArgumentException("error code: " + c.getCode());
      }
    }
  }

  private final String code;

  public static StatusCode of(String code) {
    return dataMap.get(code);
  }
}
