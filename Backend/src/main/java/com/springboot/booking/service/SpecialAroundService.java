package com.springboot.booking.service;

import com.springboot.booking.dto.response.SpecialAroundResponse;
import com.springboot.booking.repository.SpecialAroundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecialAroundService {
    private final SpecialAroundRepository specialAroundRepository;

    public List<SpecialAroundResponse> getAllSpecialAround() {
        return specialAroundRepository.findAll().stream()
                .map(specialAround -> SpecialAroundResponse.builder()
                        .specialAroundId(specialAround.getId())
                        .description(specialAround.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
