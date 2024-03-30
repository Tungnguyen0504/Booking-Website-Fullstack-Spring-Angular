package com.springboot.booking.common.paging;

import com.springboot.booking.model.Operator;
import lombok.Data;

import java.util.List;

@Data
public class FilterRequest {
    private String key;
    private List<Object> values;
    private Operator operator;
}
