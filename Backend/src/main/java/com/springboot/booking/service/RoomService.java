package com.springboot.booking.service;

import com.springboot.booking.common.AbstractConstant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.CreateRoomRequest;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.model.entity.PropertyConfig;
import com.springboot.booking.model.entity.Room;
import com.springboot.booking.repository.AccommodationRepository;
import com.springboot.booking.repository.FileRepository;
import com.springboot.booking.repository.PropertyConfigRepository;
import com.springboot.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;
    private final PropertyConfigRepository propertyConfigRepository;

    @Transactional
    public void createRoom(CreateRoomRequest request) {
        Accommodation accommodation = accommodationRepository.findById(Long.valueOf(request.getAccommodationId()))
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND, Util.extractTableName(Accommodation.class)));
        Set<PropertyConfig> propertyConfigs = new HashSet<>(propertyConfigRepository.findAll());
        StringBuilder dinningRoomBuilder = new StringBuilder();
        StringBuilder bathRoomBuilder = new StringBuilder();
        StringBuilder roomServiceBuilder = new StringBuilder();
        StringBuilder viewBuilder = new StringBuilder();

        List<Room> checkRooms = roomRepository.findByAccommodationIdAndRoomType(Long.valueOf(request.getAccommodationId()), request.getRoomType());
        if (!CollectionUtils.isEmpty(checkRooms)) {
            throw new GlobalException(ExceptionResult.CUSTOM_FIELD_EXISTED, "Tên loại phòng ở");
        }

        if (!CollectionUtils.isEmpty(request.getDinningRooms())) {
            request.getDinningRooms().forEach(str -> {
                dinningRoomBuilder.append(str + "|");
                propertyConfigs.add(PropertyConfig.builder()
                        .property(AbstractConstant.PROPERTY_DINNING_ROOM)
                        .description(str)
                        .build());
            });
        }
        if (!CollectionUtils.isEmpty(request.getBathRooms())) {
            request.getBathRooms().forEach(str -> {
                bathRoomBuilder.append(str + "|");
                propertyConfigs.add(PropertyConfig.builder()
                        .property(AbstractConstant.PROPERTY_BATH_ROOM)
                        .description(str)
                        .build());
            });
        }
        if (!CollectionUtils.isEmpty(request.getRoomServices())) {
            request.getRoomServices().forEach(str -> {
                roomServiceBuilder.append(str + "|");
                propertyConfigs.add(PropertyConfig.builder()
                        .property(AbstractConstant.PROPERTY_ROOM_SERVICE)
                        .description(str)
                        .build());
            });
        }
        if (!CollectionUtils.isEmpty(request.getViews())) {
            request.getViews().forEach(str -> {
                viewBuilder.append(str + "|");
                propertyConfigs.add(PropertyConfig.builder()
                        .property(AbstractConstant.PROPERTY_VIEW)
                        .description(str)
                        .build());
            });
        }
        propertyConfigRepository.saveAll(propertyConfigs);

        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setRoomArea(Double.parseDouble(request.getRoomArea()));
        room.setBed(request.getBed());
        room.setCapacity(Integer.parseInt(request.getCapacity()));
        room.setDinningRoom(dinningRoomBuilder.toString());
        room.setBathRoom(bathRoomBuilder.toString());
        room.setRoomService(roomServiceBuilder.toString());
        room.setSmoke(Boolean.parseBoolean(request.getSmoke()));
        room.setView(viewBuilder.toString());
        room.setPrice(Double.parseDouble(request.getPrice()));
        room.setDiscountPercent(Double.parseDouble(request.getDiscount()));
        room.setQuantity(Integer.parseInt(request.getQuantity()));
        room.setStatus(AbstractConstant.STATUS_ACTIVE);
        room.setAccommodation(accommodation);
        accommodation.setRooms(new ArrayList<>(List.of(room)));
        roomRepository.save(room);
        accommodationRepository.save(accommodation);

        request.getFiles().removeIf(file ->
                fileRepository.findByFilePath(AbstractConstant.FILE_PREFIX_ROOM + "/" + file.getOriginalFilename()).isPresent()
        );
        fileService.saveMultiple(request.getFiles(), AbstractConstant.FILE_PREFIX_ROOM);
        List<File> files = request.getFiles()
                .stream()
                .map(file -> File.builder()
                        .entityId(String.valueOf(room.getId()))
                        .entityName(Util.extractTableName(Room.class))
                        .filePath(AbstractConstant.FILE_PREFIX_ROOM + "/" + file.getOriginalFilename())
                        .build())
                .collect(Collectors.toList());
        fileRepository.saveAll(files);
    }
}
