package com.springboot.booking.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.library.common.constants.enums.BaseStatusCode;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode implements BaseStatusCode {
  FORBIDDEN_USER_PASSWORD("403-001", "response.message.forbidden.wrong.username.password"),

  BAD_REQUEST_USER_IS_INACTIVE("400-001", "response.message.user.inactive"),
  BAD_REQUEST_ROOM_TYPE_EXISTED("400-001", "response.message.room.type.existed"),

  NOT_FOUND_USER("404-001", "response.message.not.found.user");

  private final String code;
  private final String message;
}
