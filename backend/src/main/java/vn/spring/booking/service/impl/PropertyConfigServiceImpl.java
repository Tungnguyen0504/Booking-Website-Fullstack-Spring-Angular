package vn.spring.booking.service.impl;

import vn.spring.booking.dto.request.CreatePropertyConfigRequest;
import vn.spring.booking.entities.Category;
import vn.spring.booking.repository.PropertyConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyConfigServiceImpl {
    private final PropertyConfigRepository propertyConfigRepository;

    public void create(CreatePropertyConfigRequest request) {
        request.getDescriptions()
                .removeIf(des -> {
                    List<Category> propertyConfig = propertyConfigRepository.findByPropertyAndDescription(request.getProperty(), des);
                    return !CollectionUtils.isEmpty(propertyConfig);
                });
        List<Category> propertyConfigs = request.getDescriptions()
                .stream()
                .map(des -> Category.builder()
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
