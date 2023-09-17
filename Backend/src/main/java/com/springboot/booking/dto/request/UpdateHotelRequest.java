package com.springboot.booking.dto.request;

import lombok.Data;

@Data
public class UpdateHotelRequest extends HotelRequest {
    private String hotelId;
}
