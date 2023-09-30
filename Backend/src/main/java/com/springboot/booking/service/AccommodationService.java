package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.model.entity.SpecialAround;
import com.springboot.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final CityRepository cityRepository;
    private final SpecialAroundRepository specialAroundRepository;
    private final FileRepository fileRepository;

    public void createAccommodation(CreateAccommodationRequest request) {
        Set<SpecialAround> specialArounds = request.getSpecialArounds().stream()
                .filter(s -> specialAroundRepository.findSpecialAroundByDescription(s) != null)
                .map(s -> SpecialAround.create(s)).collect(Collectors.toSet());
        Accommodation accommodation = Accommodation.builder()
                .accommodationName(request.getAccommodationName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .star(request.getStar())
                .description(request.getDescription())
                .checkin(DatetimeUtil.LocaleTimeHHmm(request.getCheckin()))
                .checkout(DatetimeUtil.LocaleTimeHHmm(request.getCheckout()))
                .city(cityRepository.findById(request.getCityId()).orElse(null))
                .accommodationType(accommodationTypeRepository.findById(request.getAccommodationTypeId()).orElse(null))
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
