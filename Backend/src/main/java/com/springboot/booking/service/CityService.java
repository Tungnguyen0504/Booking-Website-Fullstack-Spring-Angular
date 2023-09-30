package com.springboot.booking.service;

import com.springboot.booking.model.entity.City;
import com.springboot.booking.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final FileService fileService;

    public List<City> getTopCity(int range) {
        return cityRepository.findTopCityOrderById(range);
    }

    public void saveImage(MultipartFile file) {
        fileService.save(file);

    }
}
