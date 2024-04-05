package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.model.entity.Room;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final FileService fileService;

    public UserResponse getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            throw new GlobalException(ExceptionResult.USER_NOT_FOUND);
        }
        String email = authentication.getName();
        if (!authentication.isAuthenticated() || email == null) {
            throw new GlobalException(ExceptionResult.USER_NOT_FOUND);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ExceptionResult.USER_NOT_FOUND));
        return transferToObject(user);
    }

    private UserResponse transferToObject(User user) {
        File file = fileService.getFilesByEntityIdAndEntityNameDesc(String.valueOf(user.getId()), Util.extractTableName(User.class));
        String fileByte = fileService.encodeFileToString(file.getFilePath());
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .role(user.getRole().name())
                .filePath(fileByte)
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
