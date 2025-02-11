package com.springboot.booking.service;

import com.springboot.booking.common.*;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.request.ResetPasswordRequest;
import com.springboot.booking.dto.request.CreateUpdateUserRequest;
import com.springboot.booking.dto.request.VerifyEmailRequest;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.EmailDetail;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.UserRepository;
import com.springboot.booking.utils.ObjectUtils;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.springboot.booking.utils.ObjectUtils.generateVerificationCode;

@Service
@RequiredArgsConstructor
public class UserService {
    private final AuthenticationFacade authenticationFacade;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final FileService fileService;
    private final AddressService addressService;
    private final EmailService emailService;

    public User getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            return null;
        }
        String email = authentication.getName();
        if (!authentication.isAuthenticated() || StringUtils.isEmpty(email)) {
            throw new GlobalException(ExceptionResult.USER_NOT_FOUND);
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ExceptionResult.USER_NOT_FOUND));
    }

    public UserResponse getCurrentUserDtoByToken(String jwt) {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            return null;
        }
        String email = jwtService.extractUsername(jwt);
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
        userRepository.save(user);

        addressService.update(user.getAddress().getId(), Long.valueOf(request.getWardId()), request.getSpecificAddress());

        fileService.executeSaveImages(request.getFiles()
                , Constant.FILE_PREFIX_USER
                , String.valueOf(user.getId())
                , ObjectUtils.extractTableName(User.class));
    }

    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "người dùng"));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public Map<String, String> verifyEmail(VerifyEmailRequest request) throws MessagingException {
        String email = "";
        if (Objects.nonNull(request) && StringUtils.isNotEmpty(request.getEmail())) {
            email = request.getEmail();
        } else {
            email = getCurrentUser().getEmail();
        }
        String verifyCode = generateVerificationCode();
        emailService.sendHtmlEmail(EmailDetail.builder()
                .recipient(email)
                .subject(Constant.MAIL_DETAIL_SUBJECT)
                .msgBody(Constant.MSG_VERIFY_CODE + verifyCode)
                .build());

        Map<String, String> response = new HashMap<>();
        response.put("verifyCode", verifyCode);
        response.put("message", SuccessResult.SEND_EMAIL_COMPLETED.getMessage());
        return response;
    }

    public UserResponse transferToObject(User user) {
        Map<String, Object> addressMap = new HashMap<>();
        if (Objects.nonNull(user.getAddress())) {
            addressMap.put("wardId", user.getAddress().getWard().getId());
            addressMap.put("specificAddress", user.getAddress().getSpecificAddress());
            addressMap.put("fullAddress", addressService.getFullAddress(user.getAddress().getId()));
        }

        List<File> files = fileService.getFiles(String.valueOf(user.getId()), ObjectUtils.extractTableName(User.class), MediaType.IMAGE_JPEG_VALUE);
        List<FileResponse> fileResponses = files
                .stream()
                .map(file -> FileResponse.builder()
                        .fileName(ObjectUtils.getFileName(file.getFilePath()))
                        .fileType(file.getFileType())
                        .base64String(fileService.encodeImageFileToString(file.getFilePath()))
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
