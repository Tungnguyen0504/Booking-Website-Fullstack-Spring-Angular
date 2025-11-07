package vn.spring.booking.common.paging;

import vn.spring.booking.constant.enums.SortDirection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SortRequest {
    private String key;
    private SortDirection direction;
}
