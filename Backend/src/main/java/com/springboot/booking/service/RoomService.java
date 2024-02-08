package com.springboot.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.booking.common.Constant;
import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.common.Util;
import com.springboot.booking.dto.request.CreateRoomRequest;
import com.springboot.booking.dto.response.RoomResponse;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.File;
import com.springboot.booking.model.entity.Room;
import com.springboot.booking.repository.AccommodationRepository;
import com.springboot.booking.repository.FileRepository;
import com.springboot.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final AccommodationRepository accommodationRepository;
    private final FileRepository fileRepository;
    private final FileService fileService;

    private final ObjectMapper mapper;

    @Transactional
    public void createRoom(CreateRoomRequest request) throws JsonProcessingException {
        Accommodation accommodation = accommodationRepository.findById(Long.valueOf(request.getAccommodationId()))
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND, Util.extractTableName(Accommodation.class)));

        List<Room> checkRooms = roomRepository.findByAccommodationIdAndRoomType(Long.valueOf(request.getAccommodationId()), request.getRoomType());
        if (!CollectionUtils.isEmpty(checkRooms)) {
            throw new GlobalException(ExceptionResult.CUSTOM_FIELD_EXISTED, "Tên loại phòng ở");
        }

        Room room = new Room();
        room.setRoomType(request.getRoomType());
        room.setRoomArea(Double.parseDouble(request.getRoomArea()));
        room.setBed(request.getBed());
        room.setCapacity(Integer.parseInt(request.getCapacity()));
        room.setDinningRoom(mapper.writeValueAsString(request.getDinningRooms()));
        room.setBathRoom(mapper.writeValueAsString(request.getBathRooms()));
        room.setRoomService(mapper.writeValueAsString(request.getRoomServices()));
        room.setSmoke(Boolean.parseBoolean(request.getSmoke()));
        room.setView(mapper.writeValueAsString(request.getViews()));
        room.setPrice(Double.parseDouble(request.getPrice()));
        room.setDiscountPercent(Double.parseDouble(request.getDiscount()));
        room.setQuantity(Integer.parseInt(request.getQuantity()));
        room.setStatus(Constant.STATUS_ACTIVE);
        room.setAccommodation(accommodation);
        accommodation.setRooms(new ArrayList<>(List.of(room)));
        roomRepository.save(room);
        accommodationRepository.save(accommodation);

        request.getFiles().removeIf(file ->
                fileRepository.findByFilePath(Constant.FILE_PREFIX_ROOM + "/" + file.getOriginalFilename()).isPresent()
        );
        fileService.saveMultiple(request.getFiles(), Constant.FILE_PREFIX_ROOM);
        List<File> files = request.getFiles()
                .stream()
                .map(file -> File.builder()
                        .entityId(String.valueOf(room.getId()))
                        .entityName(Util.extractTableName(Room.class))
                        .filePath(Constant.FILE_PREFIX_ROOM + "/" + file.getOriginalFilename())
                        .build())
                .collect(Collectors.toList());
        fileRepository.saveAll(files);
    }

    public RoomResponse getById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ExceptionResult.RESOURCE_NOT_FOUND, Util.extractTableName(Room.class)));
        return transferToObject(room);
    }

    public List<RoomResponse> getByAccommodationId(Long accommodationId) {
        List<Room> rooms = roomRepository.findByAccommodationId(accommodationId);
        if (CollectionUtils.isEmpty(rooms)) {
            return new ArrayList<>();
        }
        return rooms.stream().map(this::transferToObject).collect(Collectors.toList());
    }

    public RoomResponse transferToObject(Room room) {
        List<File> files = fileService.getFilesByEntityIdAndEntityName(String.valueOf(room.getId()), Util.extractTableName(Room.class));
        List<String> fileBytes = files.stream()
                .map(file -> {
                    try {
                        return fileService.encodeFileToString(file.getFilePath());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
        return RoomResponse.builder()
                .roomId(room.getId())
                .roomType(room.getRoomType())
                .roomArea(room.getRoomArea())
                .bed(room.getBed())
                .capacity(room.getCapacity())
                .smoke(room.isSmoke())
                .price(room.getPrice())
                .discountPercent(room.getDiscountPercent())
                .quantity(room.getQuantity())
                .status(room.getStatus())
                .views(new HashSet<>(Arrays.asList(room.getView().split("\\|"))))
                .dinningRooms(new HashSet<>(Arrays.asList(room.getDinningRoom().split("\\|"))))
                .bathRooms(new HashSet<>(Arrays.asList(room.getBathRoom().split("\\|"))))
                .roomServices(new HashSet<>(Arrays.asList(room.getRoomService().split("\\|"))))
                .filePaths(fileBytes)
                .build();
    }
}
