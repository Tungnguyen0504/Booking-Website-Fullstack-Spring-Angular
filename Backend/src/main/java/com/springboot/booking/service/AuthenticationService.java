package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.request.LoginRequest;
import com.springboot.booking.dto.request.RegisterRequest;
import com.springboot.booking.dto.request.SendVerificationCodeRequest;
import com.springboot.booking.dto.response.AuthenticationResponse;
import com.springboot.booking.exeption.HandlerException;
import com.springboot.booking.model.ERole;
import com.springboot.booking.model.ETokenType;
import com.springboot.booking.model.EmailDetail;
import com.springboot.booking.model.entity.Token;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.TokenRepository;
import com.springboot.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFacade authenticationFacade;

    public void sendVerificationCode(SendVerificationCodeRequest request) {
        EmailDetail emailDetail = EmailDetail.builder()
                .recipient(request.getEmail())
                .subject(AbstractConstant.MAIL_DETAIL_SUBJECT)
                .msgBody(AbstractConstant.getMsgBodySimple(request.getVerificationCode()))
                .build();
        emailService.sendHtmlEmail(emailDetail);
    }

    public void register(RegisterRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);

        if (Objects.nonNull(user)) {
            throw new HandlerException("Email đã tồn tại, vui lòng sử dụng địa chỉ email khác.");
        }

        user = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElse(null);

        if (Objects.nonNull(user)) {
            throw new HandlerException("Số điện thoại đã tồn tại, vui lòng sử dụng số khác.");
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
                .orElseThrow(() -> new HandlerException("Người dùng không tồn tại."));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new HandlerException("Sai mật khẩu.");
        }

        var jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .build();
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

        return userRepository.findByEmail(email).orElseThrow(() -> new HandlerException("User is not existed!"));
    }
}
