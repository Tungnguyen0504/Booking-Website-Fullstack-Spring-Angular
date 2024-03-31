package com.springboot.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.booking.common.Constant;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class AccommodationResponse {
    private Long accommodationId;
    private String accommodationName;
    private AccommodationTypeResponse accommodationType;
    private String phone;
    private String email;
    private int star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
    private String fullAddress;
    private Set<String> specialArounds;
    private Set<String> bathRooms;
    private Set<String> bedRooms;
    private Set<String> dinningRooms;
    private Set<String> languages;
    private Set<String> internets;
    private Set<String> drinkAndFoods;
    private Set<String> receptionServices;
    private Set<String> cleaningServices;
    private Set<String> pools;
    private Set<String> others;
    private List<String> filePaths;
    private List<RoomResponse> rooms;
    @JsonFormat(pattern = Constant.DATETIME_FORMAT2)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = Constant.DATETIME_FORMAT2)
    private LocalDateTime modifiedAt;
}
