package com.springboot.booking.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionResult {
    UNKNOWN(400, "Không rõ."),

    INTERNAL_ERROR(500, "Đã có lỗi xảy ra."),

    INVALID_ROLE(401, "Quyền không tồn tại."),

    INVALID_TIME(400, "Ngày giờ không hợp lệ."),

    EXISTED_ACCOUNT(400, "Tài khoản đã tồn tại."),

    EXISTED_EMAIL(400, "Địa chỉ email đã tồn tại."),

    EXISTED_PHONE_NUMBER(400, "Số điện thoại đã tồn tại."),

    WRONG_LOGIN_INFORMATION(400, "Sai thông tin đăng nhập."),

    USER_NOT_FOUND(402, "Người dùng không tồn tại."),

    UNAUTHORIZED_ERROR(403, "Bạn không có quyền truy cập."),

    LOCKED_ACCOUNT(402, "Tài khoản của bạn đã bị khóa."),

    SEND_EMAIL_ERROR(404, "Không gửi được email."),

    RESOURCE_NOT_FOUND(404, "không tìm thấy dữ liệu.")

    ;

    private Integer code;

    private String message;
}
