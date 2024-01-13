package com.springboot.booking.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class CreateRoomRequest {
    private String accommodationId;
    private String roomType;
    private String roomArea;
    private String bed;
    private String capacity;
    private String smoke;
    private String quantity;
    private String price;
    private String discount;
    private Set<String> dinningRooms;
    private Set<String> bathRooms;
    private Set<String> roomServices;
    private Set<String> views;
    private List<MultipartFile> files;
}
