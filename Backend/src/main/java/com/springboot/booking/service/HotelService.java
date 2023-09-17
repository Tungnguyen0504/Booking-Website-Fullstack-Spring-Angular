package com.springboot.booking.service;

import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.dto.request.CreateHotelRequest;
import com.springboot.booking.model.entity.Hotel;
import com.springboot.booking.model.entity.SpecialAround;
import com.springboot.booking.repository.CityRepository;
import com.springboot.booking.repository.HotelRepository;
import com.springboot.booking.repository.HotelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelTypeRepository hotelTypeRepository;
    private final CityRepository cityRepository;

    public void createHotel(CreateHotelRequest request) {
        List<SpecialAround> specialArounds = request.getSpecialArounds().stream()
                .map(s -> SpecialAround.create(s)).collect(Collectors.toList());
        Hotel hotel = Hotel.builder()
                .hotelName(request.getHotelName())
                .address(request.getAddress())
                .phone(request.getPhone())
                .email(request.getEmail())
                .star(request.getStar())
                .description(request.getDescription())
                .checkin(DatetimeUtil.LocaleTimeHHmm(request.getCheckin()))
                .checkout(DatetimeUtil.LocaleTimeHHmm(request.getCheckout()))
                .city(cityRepository.findById(request.getCityId()).orElse(null))
//                .hotelType(hotelTypeRepository.findById(request.getHotelTypeId()).orElse(null))
                .specialArounds(specialArounds)
                .build();
        hotelRepository.save(hotel);
    }
}
