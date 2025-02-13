/*
 * Copyright: Lam Nguyen
 * Position: Principal Developer
 * Email: LamNT10@ncb-bank.vn
 */
package com.springboot.booking.constant.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.library.common.constants.enums.BaseStatusCode;

@Getter
@AllArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode implements BaseStatusCode {
  FORBIDDEN_USER_NOT_EXIST("403-001", "response.message.forbidden.user.not.exist"),
  FORBIDDEN_USER_PASSWORD("403-002", "response.message.forbidden.wrong.username.password"),
  FORBIDDEN_REFRESH_TOKEN_WRONG("403-003", "response.message.forbidden.wrong.refresh.token"),
  FORBIDDEN_REFRESH_TOKEN_EXPIRE(
      "403-004", "response.message.forbidden.expire.refresh.token"),
  FORBIDDEN_ACCESS_TOKEN_WRONG("403-005", "response.message.forbidden.wrong.access.token"),
  FORBIDDEN_ACCESS_TOKEN_ALIVE("403-006", "response.message.forbidden.access.token.alive"),
  FORBIDDEN_USER_TYPE_NOT_SUPPORT(
      "403-007", "response.message.forbidden.user.type.not.support"),

  NOT_FOUND_ID("404-001", "response.message.not.found.id"),
  NOT_FOUND_CODE("404-004", "response.message.not.found.code"),
  NOT_FOUND_USERNAME("404-003", "response.message.not.found.username"),
  NOT_FOUND_PARENT_ID("404-002", "response.message.not.found.parent.id"),
  NOT_FOUND_ORGANIZATION("404-005", "response.message.not.found.organization"),
  NOT_FOUND_GROUP("404-006", "response.message.not.found.group"),
  NOT_FOUND_WORKFLOW_CODE("404-007", "response.message.not.found.workflow.code"),
  NOT_FOUND_URL("404-008", "response.message.not.found.url"),

  CONFLICT_RESULT("409-001", "response.message.conflict.to.much.result"),

  PASSWORD_IS_SAME("400-001", "response.message.password.is.same"),
  USER_TYPE_NOT_SUPPORT("400-002", "response.message.user.type.not.support"),
  PASSWORD_NOT_MATCH("400-003", "response.message.password.not.match"),
  USER_IS_INACTIVE("400-004", "response.message.user.inactive"),
  USER_LOCKED("423-001", "response.message.user.locked");

  private final String code;
  private final String message;
}
