package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.dto.response.AccommodationResponse;
import com.springboot.booking.dto.response.AccommodationTypeResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.mapper.AutoMapper;
import com.springboot.booking.model.entity.*;
import com.springboot.booking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final FileService fileService;
    private final AddressService addressService;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final SpecialAroundRepository specialAroundRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;

    public AccommodationResponse getById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND));
        return transferToDto(accommodation);
    }

    public List<AccommodationResponse> getAllAccommodationActive() {
        List<Accommodation> accommodations = accommodationRepository.findByStatus(AbstractConstant.STATUS_ACTIVE);
        if (CollectionUtils.isEmpty(accommodations)) {
            return new ArrayList<>();
        }
        return accommodations.stream().map(this::transferToDto).collect(Collectors.toList());
    }

    @Transactional
    public void createAccommodation(CreateAccommodationRequest request) {
        AccommodationType accommodationType = accommodationTypeRepository.findById(request.getAccommodationTypeId())
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND, "loại chỗ ở"));
        List<Accommodation> checkAccommodations = accommodationRepository.findByAccommodationName(request.getAccommodationName());

        if (!CollectionUtils.isEmpty(checkAccommodations)) {
            throw new GlobalException(ExceptionResult.CUSTOM_FIELD_EXISTED, "Tên " + accommodationType.getAccommodationTypeName());
        }

        Ward ward = wardRepository.findById(request.getWardId())
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND));
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
                .status(AbstractConstant.STATUS_ACTIVE)
                .accommodationType(accommodationType)
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
                fileRepository.findByFilePath(AbstractConstant.FILE_PREFIX_ACCOMMODATION + "/" + file.getOriginalFilename())
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
    }

    private AccommodationResponse transferToDto(Accommodation accommodation) {
        List<File> files = fileService.getFilesByEntityIdAndEntityName(String.valueOf(accommodation.getId()), Util.extractTableName(Accommodation.class));

        AccommodationResponse response = AutoMapper.MAPPER.mapToAccommodationResponse(accommodation);
        response.setAccommodationType(AccommodationTypeResponse.builder()
                .accommodationTypeId(accommodation.getAccommodationType().getId())
                .accommodationTypeName(accommodation.getAccommodationType().getAccommodationTypeName())
                .build());
        response.setFullAddress(addressService.getFullAddress(accommodation.getAddress().getId()));
        response.setSpecialArounds(accommodation.getSpecialArounds()
                .stream()
                .map(SpecialAround::getDescription)
                .collect(Collectors.toList()));
        response.setFilePaths(files.stream()
                .map(File::getFilePath)
                .collect(Collectors.toList()));
        return response;
    }
}
