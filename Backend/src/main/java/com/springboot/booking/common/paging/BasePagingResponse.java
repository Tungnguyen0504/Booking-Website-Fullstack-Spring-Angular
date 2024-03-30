package com.springboot.booking.common.paging;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasePagingResponse {
    private Object data;
    private int currentPage;
    private int totalPage;
    private long totalItem;
}
