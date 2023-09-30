package com.springboot.booking.service;

import com.springboot.booking.model.entity.AccommodationType;
import com.springboot.booking.repository.AccommodationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccommodationTypeSerive {
    private final AccommodationTypeRepository accommodationTypeRepository;

    public List<AccommodationType> getAllAccommodationType() {
        return accommodationTypeRepository.findAll();
    }
}
