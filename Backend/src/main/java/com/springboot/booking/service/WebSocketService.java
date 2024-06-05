package com.springboot.booking.service;

import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.dto.response.NotificationResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.Notification;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.NotificationRepository;
import com.springboot.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
