package com.springboot.booking.service;

import com.springboot.booking.dto.response.AccommodationTypeResponse;
import com.springboot.booking.model.entity.AccommodationType;
import com.springboot.booking.repository.AccommodationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;

    public List<AccommodationTypeResponse> getAllAccommodationType() {
        return accommodationTypeRepository.findAll().stream().map(accommodationType -> AccommodationTypeResponse.builder()
                        .accommodationTypeId(accommodationType.getId())
                        .accommodationTypeName(accommodationType.getAccommodationTypeName())
                        .build())
                .collect(Collectors.toList());
    }
}
