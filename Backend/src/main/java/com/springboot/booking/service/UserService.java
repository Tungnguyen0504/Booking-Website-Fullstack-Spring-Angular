package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.config.AuthenticationFacade;
import com.springboot.booking.dto.request.CreateUpdateUserRequest;
import com.springboot.booking.dto.response.UserResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.*;
import com.springboot.booking.repository.AddressRepository;
import com.springboot.booking.repository.FileRepository;
import com.springboot.booking.repository.UserRepository;
import com.springboot.booking.repository.WardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final WardRepository wardRepository;
    private final JwtService jwtService;
    private final FileService fileService;
    private final AddressService addressService;
    private final FileRepository fileRepository;

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

        request.getFiles().removeIf(file ->
                fileRepository.findByFilePath(Constant.FILE_PREFIX_USER + "/" + file.getOriginalFilename())
                        .isPresent()
        );
        fileService.saveMultiple(request.getFiles(), Constant.FILE_PREFIX_USER);
        List<File> files = request.getFiles()
                .stream()
                .map(file -> File.builder()
                        .entityId(String.valueOf(user.getId()))
                        .entityName(Util.extractTableName(Accommodation.class))
                        .filePath(Constant.FILE_PREFIX_USER + "/" + file.getOriginalFilename())
                        .build())
                .collect(Collectors.toList());
        fileRepository.saveAll(files);
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
                .fullAddress(Objects.nonNull(user.getAddress()) ? addressService.getFullAddress(user.getAddress().getId()) : "")
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
