package com.springboot.booking.service;

import com.springboot.booking.model.entity.HotelType;
import com.springboot.booking.repository.HotelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelTypeSerive {
    private final HotelTypeRepository hotelTypeRepository;

    public List<HotelType> getAllHotelType() {
        return hotelTypeRepository.findAll();
    }
}
