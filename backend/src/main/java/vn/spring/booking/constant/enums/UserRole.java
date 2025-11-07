package vn.spring.booking.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
  USER("USER"),
  SELLER("SELLER"),
  ADMIN("ADMIN");

  private static final Map<String, UserRole> dataMap = new HashMap<>();

  static {
    for (UserRole c : UserRole.values()) {
      if (dataMap.put(c.getCode(), c) != null) {
        throw new IllegalArgumentException("error code: " + c.getCode());
      }
    }
  }

  private final String code;

  public static UserRole of(String code) {
    return dataMap.get(code);
  }
}
