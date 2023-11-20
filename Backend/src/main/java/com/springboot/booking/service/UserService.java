package com.springboot.booking.service;

import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.model.BException;
import com.springboot.booking.model.entity.User;
import com.springboot.booking.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;

    public User getCurrentUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
        if (authentication == null) {
            return null;
        }
        String email = authentication.getName();
        if (!authentication.isAuthenticated() || email == null) {
            return null;
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BException(ExceptionResult.USER_NOT_FOUND));
    }
}
