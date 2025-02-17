package com.springboot.booking.service;

import static com.springboot.booking.utils.ObjectUtils.generateVerificationCode;

import com.springboot.booking.common.*;
import com.springboot.booking.constant.enums.ResponseCode;
import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.request.CreateUpdateUserRequest;
import com.springboot.booking.dto.request.ResetPasswordRequest;
import com.springboot.booking.dto.request.VerifyEmailRequest;
import com.springboot.booking.dto.response.FileResponse;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.entities.File;
import com.springboot.booking.entities.User;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.mapper.FileMapper;
import com.springboot.booking.mapper.UserMapper;
import com.springboot.booking.model.EmailDetail;
import com.springboot.booking.repository.UserRepository;
import com.springboot.booking.utils.FileUtil;
import com.springboot.booking.utils.ObjectUtils;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.library.common.exception.BaseException;

@Service
@RequiredArgsConstructor
public class UserService {
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final FileService fileService;
  private final AddressService addressService;
  private final EmailService emailService;
  private final UserMapper userMapper;
  private final FileMapper fileMapper;

  public UserDto getCurrentUser(Principal principal) {
    User user =
        userRepository
            .findByEmail(principal.getName())
            .orElseThrow(() -> new GlobalException(ExceptionResult.USER_NOT_FOUND));
    return userMapper.toDto(user);
  }

  @Transactional
  public void update(CreateUpdateUserRequest request) {
    User user =
        userRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new BaseException(ResponseCode.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPhoneNumber(request.getPhoneNumber());
    user.setDateOfBirth(DatetimeUtil.parseDateDefault(request.getDateOfBirth()));
    userRepository.save(user);

    addressService.update(
        user.getAddress().getId(), request.getWardId(), request.getSpecificAddress());

    fileService.executeSaveImages(
        request.getFiles(),
        Constant.FILE_PREFIX_USER,
        String.valueOf(user.getId()),
        ObjectUtils.extractTableName(User.class));
  }

  public void resetPassword(ResetPasswordRequest request) {
    User user =
        userRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "người dùng"));
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    userRepository.save(user);
  }

  public Map<String, String> verifyEmail(VerifyEmailRequest request, Principal principal) throws MessagingException {
    String email = "";
    if (Objects.nonNull(request) && StringUtils.isNotEmpty(request.getEmail())) {
      email = request.getEmail();
    } else {
      email = getCurrentUser(principal).getEmail();
    }
    String verifyCode = generateVerificationCode();
    emailService.sendHtmlEmail(
        EmailDetail.builder()
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
        .files(fileMapper.mapFileResponse(user.getId(), User.class, MediaType.IMAGE_JPEG_VALUE))
        .createdTime(user.getCreatedTime())
        .updatedTime(user.getUpdatedTime())
        .build();
  }
}
