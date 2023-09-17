package com.springboot.booking.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum SuccessResult {
    SEND_EMAIL_COMPLETED(200, "Đã gửi email thành công."),
    LOGOUT_SUCCESS(200, "Đăng xuất thành công."),
    CREATED(200, "Thêm mới thành công."),
    MODIFIED(200, "Thay đổi thành công."),
    DELETED(200, "Xóa thành công.");

    private Integer code;

    private String message;
}
