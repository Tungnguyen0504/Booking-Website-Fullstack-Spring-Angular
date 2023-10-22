package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.model.BException;
import com.springboot.booking.model.entity.*;
import com.springboot.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final SpecialAroundRepository specialAroundRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;

    public void createAccommodation(CreateAccommodationRequest request) {
        Ward ward = wardRepository.findById(request.getWardId())
                .orElseThrow(() -> new BException(ExceptionResult.RESOUCE_NOT_FOUND));
        Address address = addressRepository.saveAndFlush(Address.builder()
                .specificAddress(request.getSpecificAddress())
                .ward(ward)
                .build());

        Set<SpecialAround> specialArounds = request.getSpecialArounds().stream()
                .filter(s -> specialAroundRepository.findSpecialAroundByDescription(s) != null)
                .map(SpecialAround::create).collect(Collectors.toSet());

        Accommodation accommodation = Accommodation.builder()
                .accommodationName(request.getAccommodationName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .star(request.getStar())
                .description(request.getDescription())
                .checkin(DatetimeUtil.LocaleTimeHHmm(request.getCheckin()))
                .checkout(DatetimeUtil.LocaleTimeHHmm(request.getCheckout()))
                .accommodationType(accommodationTypeRepository
                        .findById(request.getAccommodationTypeId()).orElse(null))
                .address(address)
                .specialArounds(specialArounds)
                .build();
        accommodationRepository.saveAndFlush(accommodation);

        List<File> files = request.getSpecialArounds().stream()
                .map(s -> File.builder()
                        .entityId(String.valueOf(accommodation.getId()))
                        .entityName(Util.extractTableName(Accommodation.class))
                        .filePath(AbstractConstant.FILE_PREFIX_ACCOMMODATION + s)
                        .build())
                .collect(Collectors.toList());
        fileRepository.saveAll(files);
    }
}
