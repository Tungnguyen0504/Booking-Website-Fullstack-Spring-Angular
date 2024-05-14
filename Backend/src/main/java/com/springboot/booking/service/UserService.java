package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.request.CreateUpdateUserRequest;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.EmailDetail;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${environment.local.base-url}")
    private String baseUrl;

    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FileService fileService;
    private final AddressService addressService;
    private final EmailService emailService;

    public UserResponse getCurrentUser() {
        return getUserInformation("");
    }

    public UserResponse getCurrentUser(String jwt) {
        return getUserInformation(jwt);
    }

    private UserResponse getUserInformation(String jwt) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            throw new GlobalException(ExceptionResult.USER_NOT_FOUND);
        }
        String email = StringUtils.isEmpty(jwt) ? authentication.getName() : jwtService.extractUsername(jwt);
        if (!authentication.isAuthenticated() || StringUtils.isEmpty(email)) {
            throw new GlobalException(ExceptionResult.USER_NOT_FOUND);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ExceptionResult.USER_NOT_FOUND));
        return transferToObject(user);
    }

    @Transactional
    public void update(CreateUpdateUserRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "người dùng"));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateOfBirth(DatetimeUtil.parseDateDefault(request.getDateOfBirth()));
        user.setAddress(addressService.createAddress(Long.valueOf(request.getWardId()), request.getSpecificAddress()));
        userRepository.save(user);

        fileService.executeSaveFiles(request.getFiles()
                , Constant.FILE_PREFIX_USER
                , String.valueOf(user.getId())
                , Util.extractTableName(User.class));
    }

    public void sendEmailChangePassword() throws MessagingException {
        UserResponse user = getCurrentUser();

        StringBuilder body = new StringBuilder();
        body.append(Constant.MSG_CHANGE_PASSWORD);
        body.append(String.format("<a href=\"%s/user-information\">click</a>", baseUrl));

        emailService.sendHtmlEmail(EmailDetail.builder()
                .recipient(user.getEmail())
                .subject(Constant.MAIL_DETAIL_SUBJECT)
                .msgBody(body.toString())
                .build());
    }

    private UserResponse transferToObject(User user) {
        Map<String, Object> addressMap = new HashMap<>();
        if (Objects.nonNull(user.getAddress())) {
            addressMap.put("wardId", user.getAddress().getWard().getId());
            addressMap.put("specificAddress", user.getAddress().getSpecificAddress());
            addressMap.put("fullAddress", addressService.getFullAddress(user.getAddress().getId()));
        }

        List<File> files = fileService.getFiles(String.valueOf(user.getId()), Util.extractTableName(User.class), MediaType.IMAGE_JPEG_VALUE);
        List<FileResponse> fileResponses = files
                .stream()
                .map(file -> FileResponse.builder()
                        .fileName(file.getFileName())
                        .fileType(file.getFileType())
                        .base64String(fileService.encodeFileToString(file.getFilePath()))
                        .build())
                .toList();

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .address(addressMap)
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .status(user.getStatus())
                .role(user.getRole().name())
                .files(fileResponses)
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
