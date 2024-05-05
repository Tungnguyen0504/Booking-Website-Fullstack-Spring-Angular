package com.springboot.booking.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class CreateAccommodationRequest {
    private String accommodationName;
    private String accommodationTypeId;
    private String phone;
    private String email;
    private String star;
    private String description;
    private LocalTime checkin;
    private LocalTime checkout;
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
    private String wardId;
    private String specificAddress;
    private List<MultipartFile> files;
}
