package com.springboot.booking.service;

import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.common.paging.SortRequest;
import com.springboot.booking.model.SortDirection;
import com.springboot.booking.model.entity.Accommodation;
import jakarta.persistence.criteria.Path;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class PagingService {
    public <T> List<Specification<T>> buildSpecifications(BasePagingRequest request) {
        List<Specification<T>> specifications = new ArrayList<>();
        request.getFilterRequest().forEach(req -> specifications.add(req.getOperator().build(req.getKey(), req.getValues())));
        return specifications;
    }

    public Sort buildOrders(BasePagingRequest request) {
        List<Sort.Order> orders = new ArrayList<>();
        if (CollectionUtils.isEmpty(request.getSortRequest())) {
            request.setSortRequest(Collections.singletonList(SortRequest.builder()
                    .key("modifiedAt")
                    .direction(SortDirection.DESC)
                    .build()));
        }
        request.getSortRequest().forEach(req -> orders.add(req.getDirection().build(req.getKey())));
        return Sort.by(orders);
    }
}
