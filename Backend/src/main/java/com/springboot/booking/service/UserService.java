package com.springboot.booking.service;

import static com.springboot.booking.utils.ObjectUtils.generateVerificationCode;

import com.springboot.booking.common.*;
import com.springboot.booking.constant.AppConst;
import com.springboot.booking.constant.enums.ResponseCode;
import com.springboot.booking.dto.UserDto;
import com.springboot.booking.dto.request.ResetPasswordRequest;
import com.springboot.booking.dto.request.UpdateUserRequest;
import com.springboot.booking.dto.request.VerifyEmailRequest;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.entities.User;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.mapper.UserMapper;
import com.springboot.booking.model.EmailDetail;
import com.springboot.booking.repository.UserRepository;
import jakarta.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.library.common.exception.BaseException;
import vn.library.common.utils.CryptoUtil;

@Service
@RequiredArgsConstructor
public class UserService {
  private final FileService fileService;
  private final AddressService addressService;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserResponse getCurrentUser(Principal principal) {
    User user =
        userRepository
            .findByEmail(principal.getName())
            .orElseThrow(() -> new GlobalException(ExceptionResult.USER_NOT_FOUND));
    return userMapper.toResponse(user);
  }

  public UserDto read(UUID id) {
    return userRepository
        .findById(id)
        .map(userMapper::toDto)
        .orElseThrow(() -> new BaseException(ResponseCode.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
  }

  @Transactional
  public void update(UpdateUserRequest request, Principal principal) throws IOException {
    User user =
        userRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new BaseException(ResponseCode.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
    userMapper.toEntityModify(user, request, principal);
    userRepository.save(user);

    addressService.update(
        user.getAddress().getId(),
        request.getAddress().getWardId(),
        request.getAddress().getSpecificAddress());

    fileService.executeSaveFile(request.getFiles(), user.getId(), User.class, AppConst.PATH_USER);
  }

  public void resetPassword(ResetPasswordRequest request) {
    User user =
        userRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new BaseException(ResponseCode.NOT_FOUND_USER, HttpStatus.NOT_FOUND));
    user.setPassword(CryptoUtil.encryptPassword(CryptoUtil.decrypt(request.getPassword())));
    userRepository.save(user);
  }

  public Map<String, String> verifyEmail(VerifyEmailRequest request, Principal principal)
      throws MessagingException {
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
}
