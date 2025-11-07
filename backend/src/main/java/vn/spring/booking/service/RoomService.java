package vn.spring.booking.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import vn.spring.booking.common.Constant;
import vn.spring.booking.constant.enums.ResponseCode;
import vn.spring.booking.utils.ObjectUtils;
import vn.spring.booking.dto.request.CreateUpdateRoomRequest;
import vn.spring.booking.dto.response.RoomResponse;
import vn.spring.booking.entities.Accommodation;
import vn.spring.booking.entities.File;
import vn.spring.booking.entities.Room;
import vn.spring.booking.repository.AccommodationRepository;
import vn.spring.booking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import vn.spring.common.constants.enums.BaseResponseCode;
import vn.spring.common.exception.BaseException;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepository;
  private final AccommodationRepository accommodationRepository;
  private final FileService fileService;

  private final ObjectMapper mapper;

  @Transactional
  public void createRoom(CreateUpdateRoomRequest request) throws JsonProcessingException {
    Accommodation accommodation =
        accommodationRepository
            .findById(request.getAccommodationId())
            .orElseThrow(() -> new BaseException(BaseResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));

    List<Room> checkRooms =
        roomRepository.findByAccommodationIdAndRoomType(
            request.getAccommodationId(), request.getRoomType());
    if (!CollectionUtils.isEmpty(checkRooms)) {
      throw new BaseException(ResponseCode.BAD_REQUEST_ROOM_TYPE_EXISTED, HttpStatus.NOT_FOUND);
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

    fileService.executeSaveImages(
        request.getFiles(),
        Constant.FILE_PREFIX_ROOM,
        String.valueOf(room.getId()),
        ObjectUtils.extractTableName(Room.class));
  }

  public RoomResponse getById(UUID id) {
    Room room =
        roomRepository
            .findById(id)
            .orElseThrow(() -> new BaseException(BaseResponseCode.NOT_FOUND, HttpStatus.NOT_FOUND));
    return transferToObject(room);
  }

  public List<RoomResponse> getByAccommodationId(Long accommodationId) {
    List<Room> rooms = roomRepository.findByAccommodationId(accommodationId);
    if (CollectionUtils.isEmpty(rooms)) {
      return new ArrayList<>();
    }
    return rooms.stream().map(this::transferToObject).collect(Collectors.toList());
  }

  @SneakyThrows
  public RoomResponse transferToObject(Room room) {
    List<File> files =
        fileService.getFilesByEntityIdAndEntityName(
            String.valueOf(room.getId()), ObjectUtils.extractTableName(Room.class));
    List<String> fileBytes =
        files.stream()
            .map(file -> fileService.encodeImageFileToString(file.getFilePath()))
            .toList();
    return RoomResponse.builder()
        .roomId(room.getId())
        .accommodationId(room.getAccommodation().getId())
        .roomType(room.getRoomType())
        .roomArea(room.getRoomArea())
        .bed(room.getBed())
        .capacity(room.getCapacity())
        .smoke(room.getSmoke())
        .price(room.getPrice())
        .discountPercent(room.getDiscountPercent())
        .quantity(room.getQuantity())
        .status(room.getStatus())
        .views(mapper.readValue(room.getView(), Set.class))
        .dinningRooms(mapper.readValue(room.getDinningRoom(), Set.class))
        .bathRooms(mapper.readValue(room.getBathRoom(), Set.class))
        .roomServices(mapper.readValue(room.getRoomService(), Set.class))
        .filePaths(fileBytes)
        .build();
  }
}
