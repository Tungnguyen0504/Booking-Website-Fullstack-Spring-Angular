package com.springboot.booking.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionResult {
    CREATED_ERROR(400, "Thêm mới thất bại."),
    MODIFIED_ERROR(400, "Cập nhật thất bại."),
    DELETED_ERROR(400, "Xóa thất bại."),
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
    CUSTOM_FIELD_EXISTED(409, "%s đã tồn tại"),
    CUSTOM_FIELD_NOT_FOUND(409, "Không tìm thấy %s"),
    RESOURCE_NOT_FOUND(404, "[%s] không tìm thấy dữ liệu."),
    PARAMETER_INVALID(400, "Tham số không hợp lệ");

    private Integer code;

    private String message;
}
