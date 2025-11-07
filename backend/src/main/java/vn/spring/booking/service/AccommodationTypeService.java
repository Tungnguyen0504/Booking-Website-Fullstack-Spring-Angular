package vn.spring.booking.service;

import vn.spring.booking.dto.response.AccommodationTypeResponse;
import vn.spring.booking.repository.AccommodationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationTypeService {
    private final AccommodationTypeRepository accommodationTypeRepository;

    public List<AccommodationTypeResponse> getAllAccommodationType() {
        return accommodationTypeRepository.getAll().stream()
                .map(accommodationType -> AccommodationTypeResponse.builder()
                        .accommodationTypeId(accommodationType.getId())
                        .accommodationTypeName(accommodationType.getAccommodationTypeName())
                        .build())
                .collect(Collectors.toList());
    }
}
