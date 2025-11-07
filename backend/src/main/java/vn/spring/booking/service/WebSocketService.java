package vn.spring.booking.service;

import vn.spring.booking.common.ExceptionResult;
import vn.spring.booking.dto.response.NotificationResponse;
import vn.spring.booking.exeption.GlobalException;
import vn.spring.booking.entities.Notification;
import vn.spring.booking.entities.User;
import vn.spring.booking.repository.NotificationRepository;
import vn.spring.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public void push(String destination, String message, String url, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GlobalException(ExceptionResult.USER_NOT_FOUND));

        Notification notification = notificationRepository.save(Notification.builder()
                .message(message)
                .url(url)
                .user(user)
                .build());
        simpMessagingTemplate.convertAndSend(destination, NotificationResponse.builder()
                .message(message)
                .url(url)
                .user(userService.transferToObject(user))
                .build());
    }
}
