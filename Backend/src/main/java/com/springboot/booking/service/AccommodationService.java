package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.model.BException;
import com.springboot.booking.model.entity.*;
import com.springboot.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final FileService fileService;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final SpecialAroundRepository specialAroundRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;

    @Transactional
    public void createAccommodation(CreateAccommodationRequest request) {
        try {
            Ward ward = wardRepository.findById(request.getWardId())
                    .orElseThrow(() -> new BException(ExceptionResult.RESOUCE_NOT_FOUND));
            Address address = addressRepository.save(Address.builder()
                    .specificAddress(request.getSpecificAddress())
                    .ward(ward)
                    .build());

            Accommodation accommodation = Accommodation.builder()
                    .accommodationName(request.getAccommodationName())
                    .phone(request.getPhone())
                    .email(request.getEmail())
                    .star(request.getStar())
                    .description(request.getDescription())
                    .checkin(request.getCheckin())
                    .checkout(request.getCheckout())
                    .accommodationType(accommodationTypeRepository
                            .findById(request.getAccommodationTypeId()).orElse(null))
                    .address(address)
                    .build();
            Set<SpecialAround> specialArounds = request.getSpecialArounds().stream()
                    .map(s -> {
                        SpecialAround specialAround = specialAroundRepository.findSpecialAroundByDescription(s).orElse(null);
                        if (specialAround == null) {
                            specialAround = SpecialAround.builder()
                                    .description(s)
                                    .accommodations(new HashSet<>())
                                    .build();
                        }
                        specialAround.getAccommodations().add(accommodation);
                        return specialAround;
                    })
                    .collect(Collectors.toSet());
            accommodation.setSpecialArounds(specialArounds);
            accommodationRepository.save(accommodation);
            specialAroundRepository.saveAll(specialArounds);


            request.getFiles().removeIf(file ->
                    fileRepository.findByFilePath(
                                    AbstractConstant.FILE_PREFIX_ACCOMMODATION + "/" + file.getOriginalFilename())
                            .isPresent()
            );
            fileService.saveMultiple(request.getFiles(), AbstractConstant.FILE_PREFIX_ACCOMMODATION);
            List<File> files = request.getFiles()
                    .stream()
                    .map(file -> File.builder()
                            .entityId(String.valueOf(accommodation.getId()))
                            .entityName(Util.extractTableName(Accommodation.class))
                            .filePath(AbstractConstant.FILE_PREFIX_ACCOMMODATION + "/" + file.getOriginalFilename())
                            .build())
                    .collect(Collectors.toList());
            fileRepository.saveAll(files);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BException("Không thể tạo mới chỗ ở.");
        }
    }
}
