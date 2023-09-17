package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.dto.request.VerificationRequest;
import com.springboot.booking.dto.response.AuthenticationResponse;
import com.springboot.booking.model.BException;
import com.springboot.booking.model.ERole;
import com.springboot.booking.model.ETokenType;
import com.springboot.booking.model.EmailDetail;
import com.springboot.booking.model.entity.Token;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.TokenRepository;
import com.springboot.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFacade authenticationFacade;

    public String verification(VerificationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BException(ExceptionResult.WRONG_LOGIN_INFORMATION);
        }

        String verifyCode = Util.generateVerificationCode();

        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(request.getEmail())
                .subject(AbstractConstant.MAIL_DETAIL_SUBJECT)
                .msgBody(AbstractConstant.getMsgBodySimple(verifyCode))
                .build();
        emailService.sendHtmlEmail(emailDetail);

        return verifyCode;
    }

    public void register(RegisterRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (Objects.nonNull(user)) {
            throw new BException(ExceptionResult.EXISTED_EMAIL);
        }

        user = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElse(null);

        if (Objects.nonNull(user)) {
            throw new BException(ExceptionResult.EXISTED_PHONE_NUMBER);
        }

        user = User.builder()
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ERole.USER)
                .build();

        userRepository.save(user);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new BException(ExceptionResult.USER_NOT_FOUND));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
            authenticationFacade.setAuthentication(authentication);
        } catch (AuthenticationException e) {
            throw new BException(ExceptionResult.WRONG_LOGIN_INFORMATION);
        }

        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    public void logout(String jwt) {
        Token tokenStored = tokenRepository.findByToken(jwt).orElse(null);
        if (tokenStored != null) {
            tokenRepository.save(tokenStored.builder()
                    .expired(true)
                    .revoked(true)
                    .build());
            authenticationFacade.clearContext();
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(ETokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;

        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }

    public User getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null)
            return null;

        String email = authentication.getName();
        if (!authentication.isAuthenticated() || email == null)
            return null;

        return userRepository.findByEmail(email).orElseThrow(() -> new BException(ExceptionResult.USER_NOT_FOUND));
    }
}
