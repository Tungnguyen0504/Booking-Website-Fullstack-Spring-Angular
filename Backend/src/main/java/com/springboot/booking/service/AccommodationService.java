package com.springboot.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.booking.common.Constant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.common.paging.BasePagingResponse;
import com.springboot.booking.dto.request.SearchAccommodationRequest;
import com.springboot.booking.dto.request.CreateAccommodationRequest;
import com.springboot.booking.dto.response.AccommodationResponse;
import com.springboot.booking.dto.response.AccommodationTypeResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.mapper.AutoMapper;
import com.springboot.booking.model.entity.*;
import com.springboot.booking.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final FileService fileService;
    private final AddressService addressService;
    private final RoomService roomService;
    private final PagingService pagingService;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationTypeRepository accommodationTypeRepository;
    private final WardRepository wardRepository;
    private final AddressRepository addressRepository;
    private final FileRepository fileRepository;

    private final ObjectMapper mapper;

    public AccommodationResponse getById(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "chỗ ở"));
        return transferToDto(accommodation);
    }

    public List<AccommodationResponse> getAllAccommodationActive() {
        List<Accommodation> accommodations = accommodationRepository.getAll();
        if (CollectionUtils.isEmpty(accommodations)) {
            return new ArrayList<>();
        }
        return accommodations.stream().map(this::transferToDto).collect(Collectors.toList());
    }

//    public BasePagingResponse getAccommodations(BasePagingRequest request) {
//        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getTotalPage(), pagingService.buildOrders(request));
//        Page<Accommodation> accommodationPage = accommodationRepository.findAll(
//                Specification.allOf(pagingService.buildSpecifications(request)), pageable);
//        return BasePagingResponse.builder()
//                .data(accommodationPage.getContent().stream().map(this::transferToDto).collect(Collectors.toList()))
//                .currentPage(accommodationPage.getPageable().getPageNumber())
//                .totalItem(accommodationPage.getTotalElements())
//                .totalPage(accommodationPage.getPageable().getPageSize())
//                .build();
//    }

    public BasePagingResponse getAccommodations(SearchAccommodationRequest request) {
        Pageable pageable = PageRequest.of(request.getCurrentPage(), request.getTotalPage());

        StringBuilder fromTableBuilder = new StringBuilder("accommodation acc ");
        StringBuilder whereBuilder = new StringBuilder("WHERE 1 = 1 ");
        StringBuilder orderBuilder = new StringBuilder("ORDER BY ");

        buildWhereCondition(request, fromTableBuilder, whereBuilder);
        buildOrderCondition(request, fromTableBuilder, orderBuilder);

        System.out.println(toQuery(fromTableBuilder, whereBuilder, orderBuilder));

        Page<Accommodation> accommodationPage = accommodationRepository
                .pagingAccommodations(toQuery(fromTableBuilder, whereBuilder, orderBuilder), pageable);
        return BasePagingResponse.builder()
                .data(accommodationPage.getContent().stream().map(this::transferToDto).collect(Collectors.toList()))
                .currentPage(accommodationPage.getPageable().getPageNumber())
                .totalItem(accommodationPage.getTotalElements())
                .totalPage(accommodationPage.getPageable().getPageSize())
                .build();
    }

    private String toQuery(StringBuilder... builders) {
        return Arrays.stream(builders).reduce(StringBuilder::append)
                .orElseThrow(NullPointerException::new)
                .toString();
    }

    private void buildWhereCondition(SearchAccommodationRequest request, StringBuilder fromTableBuilder, StringBuilder whereBuilder) {
        if (request.getFilterRequest().containsKey("accommodationType")) {
            whereBuilder.append(String.format("AND acc.accommodation_type_id IN (%s) \n",
                    StringUtils.arrayToDelimitedString(request.getFilterRequest().get("accommodationType").toArray(), ", ")));
        }
        if (request.getFilterRequest().containsKey("star")) {
            whereBuilder.append(String.format("AND acc.star IN (%s) \n",
                    StringUtils.arrayToDelimitedString(request.getFilterRequest().get("star").toArray(), ", ")));
        }
    }

    private void buildOrderCondition(SearchAccommodationRequest request, StringBuilder fromTableBuilder, StringBuilder orderBuilder) {
        String key = "acc.modified_at";
        String direction = "DESC";
        if (Objects.nonNull(request.getSortRequest())) {
            switch (String.valueOf(request.getSortRequest().get("key"))) {
                case "option1":
                case "option2":
                    fromTableBuilder.append("JOIN room r ON acc.accommodation_id = r.accommodation_id \n");
                    key = "(r.price * (100 - r.discount_percent) / 100)";
                case "option3":
                case "option4":
                case "option5":
            }
            direction = String.valueOf(request.getSortRequest().get("direction"));
        }
        orderBuilder.append(String.format("%s %s \n", key, direction));
    }

    @Transactional
    public void createAccommodation(CreateAccommodationRequest request) throws JsonProcessingException {
        AccommodationType accommodationType = accommodationTypeRepository.findById(Long.valueOf(request.getAccommodationTypeId()))
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "loại chỗ ở"));
        List<Accommodation> checkAccommodations = accommodationRepository.getByAccommodationName(request.getAccommodationName());

        if (!CollectionUtils.isEmpty(checkAccommodations)) {
            throw new GlobalException(ExceptionResult.CUSTOM_FIELD_EXISTED, "Tên " + accommodationType.getAccommodationTypeName());
        }

        Ward ward = wardRepository.findById(Long.valueOf(request.getWardId()))
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND));
        Address address = addressRepository.save(Address.builder()
                .specificAddress(request.getSpecificAddress())
                .ward(ward)
                .build());

        Accommodation accommodation = Accommodation.builder()
                .accommodationName(request.getAccommodationName())
                .phone(request.getPhone())
                .email(request.getEmail())
                .star(Integer.valueOf(request.getStar()))
                .description(request.getDescription())
                .checkin(request.getCheckin())
                .checkout(request.getCheckout())
                .specialAround(mapper.writeValueAsString(request.getSpecialArounds()))
                .bathRoom(mapper.writeValueAsString(request.getBathRooms()))
                .bedRoom(mapper.writeValueAsString(request.getBedRooms()))
                .dinningRoom(mapper.writeValueAsString(request.getDinningRooms()))
                .language(mapper.writeValueAsString(request.getLanguages()))
                .internet(mapper.writeValueAsString(request.getInternets()))
                .drinkAndFood(mapper.writeValueAsString(request.getDrinkAndFoods()))
                .receptionService(mapper.writeValueAsString(request.getReceptionServices()))
                .cleaningService(mapper.writeValueAsString(request.getCleaningServices()))
                .pool(mapper.writeValueAsString(request.getPools()))
                .other(mapper.writeValueAsString(request.getOthers()))
                .status(Constant.STATUS_ACTIVE)
                .accommodationType(accommodationType)
                .address(address)
                .build();
        accommodationRepository.save(accommodation);

        request.getFiles().removeIf(file ->
                fileRepository.findByFilePath(Constant.FILE_PREFIX_ACCOMMODATION + "/" + file.getOriginalFilename())
                        .isPresent()
        );
        fileService.saveMultiple(request.getFiles(), Constant.FILE_PREFIX_ACCOMMODATION);
        List<File> files = request.getFiles()
                .stream()
                .map(file -> File.builder()
                        .entityId(String.valueOf(accommodation.getId()))
                        .entityName(Util.extractTableName(Accommodation.class))
                        .filePath(Constant.FILE_PREFIX_ACCOMMODATION + "/" + file.getOriginalFilename())
                        .build())
                .collect(Collectors.toList());
        fileRepository.saveAll(files);
    }

    public void inactive(Long id) {
        Accommodation accommodation = accommodationRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ExceptionResult.CUSTOM_FIELD_NOT_FOUND, "chỗ ở"));
        accommodation.setStatus(Constant.STATUS_INACTIVE);
        accommodationRepository.save(accommodation);
    }

    @SneakyThrows(JsonProcessingException.class)
    private AccommodationResponse transferToDto(Accommodation accommodation) {
        List<File> files = fileService.getFilesByEntityIdAndEntityName(String.valueOf(accommodation.getId()), Util.extractTableName(Accommodation.class));
        List<String> fileBytes = files.stream()
                .map(file -> fileService.encodeFileToString(file.getFilePath()))
                .toList();

        AccommodationResponse response = AutoMapper.MAPPER.mapToAccommodationResponse(accommodation);
        response.setAccommodationType(
                AccommodationTypeResponse.builder()
                        .accommodationTypeId(accommodation.getAccommodationType().getId())
                        .accommodationTypeName(accommodation.getAccommodationType().getAccommodationTypeName())
                        .build()
        );
        response.setFullAddress(addressService.getFullAddress(accommodation.getAddress().getId()));
        response.setSpecialArounds(mapper.readValue(accommodation.getSpecialAround(), Set.class));
        response.setBathRooms(mapper.readValue(accommodation.getBathRoom(), Set.class));
        response.setBedRooms(mapper.readValue(accommodation.getBedRoom(), Set.class));
        response.setDinningRooms(mapper.readValue(accommodation.getDinningRoom(), Set.class));
        response.setLanguages(mapper.readValue(accommodation.getLanguage(), Set.class));
        response.setInternets(mapper.readValue(accommodation.getInternet(), Set.class));
        response.setDrinkAndFoods(mapper.readValue(accommodation.getDrinkAndFood(), Set.class));
        response.setReceptionServices(mapper.readValue(accommodation.getReceptionService(), Set.class));
        response.setCleaningServices(mapper.readValue(accommodation.getCleaningService(), Set.class));
        response.setPools(mapper.readValue(accommodation.getPool(), Set.class));
        response.setOthers(mapper.readValue(accommodation.getOther(), Set.class));
        response.setFilePaths(fileBytes);
        response.setRooms(
                accommodation.getRooms().stream()
                        .map(roomService::transferToObject)
                        .collect(Collectors.toList())
        );
        return response;
    }
}
