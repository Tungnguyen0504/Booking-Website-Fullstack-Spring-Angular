package com.springboot.booking.dto.request;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SearchAccommodationRequest {
    private Map<String, List<Object>> filterRequest;
    private Map<String, Object> sortRequest;
    private int currentPage;
    private int totalPage;
}
