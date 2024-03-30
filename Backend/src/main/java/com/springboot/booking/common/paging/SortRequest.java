package com.springboot.booking.common.paging;

import com.springboot.booking.model.SortDirection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortRequest {
    private String key;
    private SortDirection direction;
}
