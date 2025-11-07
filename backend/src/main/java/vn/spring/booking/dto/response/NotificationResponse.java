package vn.spring.booking.dto.response;

import lombok.*;

@Data
@Builder
public class NotificationResponse {
    private String message;
    private String url;
    private UserResponse user;
}
