package com.springboot.booking.service;

import static vn.library.common.constants.SecurityConst.*;
import static vn.library.common.constants.enums.TokenAuthCode.ACCESS_TOKEN;
import static vn.library.common.constants.enums.TokenAuthCode.REFRESH_TOKEN;

import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.constant.enums.ResponseCode;
import com.springboot.booking.constant.enums.StatusCode;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.entities.File;
import com.springboot.booking.entities.User;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.mapper.UserMapper;
import com.springboot.booking.repository.FileRepository;
import com.springboot.booking.repository.UserRepository;
import com.springboot.booking.strategies.LoginStrategies;
import com.springboot.booking.utils.ObjectUtils;
import io.jsonwebtoken.lang.Strings;
import java.security.Principal;
import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.library.common.components.JwtEngine;
import vn.library.common.components.RedisCacheEngine;
import vn.library.common.constants.enums.BaseResponseCode;
import vn.library.common.exception.BaseException;
import vn.library.common.exception.ForbiddenException;
import vn.library.common.model.AuthorizationModel;
import vn.library.common.utils.CryptoUtil;
import vn.library.common.utils.ObjectUtil;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
  private final UserRepository userRepository;
  private final FileRepository fileRepository;
  private final UserMapper userMapper;
  private final JwtEngine jwtEngine;
  private final RedisCacheEngine cacheEngine;
  private final List<LoginStrategies> loginStrategies;

  @Transactional
  public void register(RegisterRequest request, Principal principal) {
    if (userRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new GlobalException(ExceptionResult.EXISTED_EMAIL);
    }

    if (userRepository.findByPhoneNumber(request.getPhoneNumber()).isPresent()) {
      throw new GlobalException(ExceptionResult.EXISTED_PHONE_NUMBER);
    }

    User user = userRepository.save(userMapper.toEntity(request, principal));

    fileRepository.save(
        File.builder()
            .entityId(user.getId())
            .entityName(ObjectUtils.extractTableName(User.class))
            .fileType(MediaType.IMAGE_JPEG_VALUE)
            .filePath("user/user-default.jpg")
            .build());
  }

  @Transactional
  public AuthorizationModel login(LoginRequest request) {
    User user =
        userRepository
            .findByEmail(request.getEmail())
            .orElseThrow(() -> new BaseException(BaseResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));
    log.info(
        "user: {} login with type {}, locked {}",
        request.getEmail(),
        String.format(
            "[%s]", Strings.arrayToDelimitedString(user.getAuthorities().toArray(), ", ")),
        user.getStatus());
    if (StatusCode.INACTIVE.equals(user.getStatus()))
      throw new BaseException(ResponseCode.BAD_REQUEST_USER_IS_INACTIVE, HttpStatus.BAD_REQUEST);

    boolean isLogin =
        loginStrategies.stream()
            .filter(m -> m.getLoginMethod().equals(request.getMethod()))
            .findFirst()
            .orElseThrow(() -> new ForbiddenException(ResponseCode.FORBIDDEN_USER_TYPE_NOT_SUPPORT))
            .execute(user, request);
    if (!isLogin)
      throw new BaseException(ResponseCode.FORBIDDEN_USER_PASSWORD, HttpStatus.BAD_REQUEST);

    return generateTokenAndSaveCache(user, null);
  }

  public void logout(Principal principal) {
    cacheEngine.delete(CryptoUtil.encryptKeyCache(ACCESS_TOKEN.getCode(), principal.getName()));
    cacheEngine.delete(CryptoUtil.encryptKeyCache(REFRESH_TOKEN.getCode(), principal.getName()));
    SecurityContextHolder.clearContext();
  }

  private AuthorizationModel generateTokenAndSaveCache(User user, UUID refreshToken) {
    AuthorizationModel authorizationModel;
    if (ObjectUtil.isEmpty(refreshToken)) {
      authorizationModel =
          jwtEngine.generateToken(
              user.getId(),
              user.getUsername(),
              String.format("%s %s", user.getFirstName(), user.getLastName()),
              getClaims(user));
    } else {
      authorizationModel =
          jwtEngine.generateTokenOnly(
              user.getId(),
              user.getUsername(),
              String.format("%s %s", user.getFirstName(), user.getLastName()),
              getClaims(user));
      authorizationModel.setRefreshToken(refreshToken);
      var expireRefresh =
          cacheEngine.getExpire(
              CryptoUtil.encryptKeyCache(REFRESH_TOKEN.getCode(), user.getUsername()));
      if (expireRefresh == null || expireRefresh <= 0) {
        throw new ForbiddenException(ResponseCode.FORBIDDEN_REFRESH_TOKEN_EXPIRE);
      }
      authorizationModel.setExpRefresh(expireRefresh);
    }
    log.info("authorizationModel generate {}", authorizationModel);
    cacheEngine.set(
        CryptoUtil.encryptKeyCache(ACCESS_TOKEN.getCode(), user.getUsername()),
        authorizationModel.getAccessToken(),
        authorizationModel.getExp());
    cacheEngine.set(
        CryptoUtil.encryptKeyCache(REFRESH_TOKEN.getCode(), user.getUsername()),
        authorizationModel.getRefreshToken().toString(),
        authorizationModel.getExpRefresh());
    return authorizationModel;
  }

  private Map<String, Object> getClaims(User user) {
    var claims = new HashMap<String, Object>();
    if (ObjectUtil.isNotEmpty(user.getRoles())) {
      claims.put(ROLES, user.getAuthorities());
    }
    if (ObjectUtil.isNotEmpty(user.getEmail())) {
      claims.put(EMAIL, user.getEmail());
    }
    if (ObjectUtil.isNotEmpty(user.getPhoneNumber())) {
      claims.put(PHONE, user.getPhoneNumber());
    }
    return claims;
  }
}
