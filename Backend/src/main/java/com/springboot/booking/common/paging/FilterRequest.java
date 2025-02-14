package com.springboot.booking.common.paging;

import com.springboot.booking.constant.enums.FieldType;
import com.springboot.booking.model.Operator;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FilterRequest {
    private String key;
    private List<String> values;
    private Operator operator;
    private FieldType fieldType;
}
