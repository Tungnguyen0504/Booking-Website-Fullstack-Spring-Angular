package vn.spring.booking.common.paging;

import lombok.Data;

import java.util.List;

@Data
public class BasePagingRequest {
    private List<FilterRequest> filterRequest;
    private List<SortRequest> sortRequest;
    private int currentPage;
    private int totalPage;
}
