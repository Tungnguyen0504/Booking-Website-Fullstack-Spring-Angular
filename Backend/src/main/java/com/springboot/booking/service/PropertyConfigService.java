package com.springboot.booking.service;

import com.springboot.booking.dto.request.CreatePropertyConfigRequest;
import com.springboot.booking.entities.PropertyConfig;
import com.springboot.booking.repository.PropertyConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyConfigService {
    private final PropertyConfigRepository propertyConfigRepository;

    public void create(CreatePropertyConfigRequest request) {
        request.getDescriptions()
                .removeIf(des -> {
                    List<PropertyConfig> propertyConfig = propertyConfigRepository.findByPropertyAndDescription(request.getProperty(), des);
                    return !CollectionUtils.isEmpty(propertyConfig);
                });
        List<PropertyConfig> propertyConfigs = request.getDescriptions()
                .stream()
                .map(des -> PropertyConfig.builder()
                        .property(request.getProperty())
                        .description(des)
                        .build())
                .toList();
        propertyConfigRepository.saveAll(propertyConfigs);
    }

    public List<String> getByProperty(String property) {
        return propertyConfigRepository.findByPropertyDistinct(property);
    }
}
