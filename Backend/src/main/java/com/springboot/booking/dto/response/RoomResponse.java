package com.springboot.booking.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RoomResponse {
    private UUID roomId;
    private UUID accommodationId;
    private String roomType;
    private double roomArea;
    private String bed;
    private int capacity;
    private boolean smoke;
    private double price;
    private double discountPercent;
    private int quantity;
    private String status;
    private Set<String> views;
    private Set<String> dinningRooms;
    private Set<String> bathRooms;
    private Set<String> roomServices;
    private List<String> filePaths;
}
