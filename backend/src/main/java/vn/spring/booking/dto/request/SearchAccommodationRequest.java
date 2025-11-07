package vn.spring.booking.dto.request;

import vn.spring.booking.common.paging.BasePagingRequest;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SearchAccommodationRequest extends BasePagingRequest {
    private Map<String, List<Object>> customFilterRequest;
    private String customSortOption;
}
