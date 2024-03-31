package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.common.paging.FilterRequest;
import com.springboot.booking.common.paging.SortRequest;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.FieldType;
import com.springboot.booking.model.Operator;
import com.springboot.booking.model.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagingService {
    public <T, V extends Comparable<? super V>> List<Specification<T>> buildSpecifications(BasePagingRequest request) {
        List<Specification<T>> specifications = new ArrayList<>();
        request.getFilterRequest().forEach(req -> {
            List<V> values = req.getValues()
                    .stream()
                    .map(val -> req.getFieldType().<V>parse(val))
                    .collect(Collectors.toList());
            specifications.add(req.getOperator().build(req.getKey(), values));
        });
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

    public void hasActiveCondition(BasePagingRequest request) {
        List<FilterRequest> list = request.getFilterRequest();
        list.add(FilterRequest.builder()
                .key("status")
                .values(Collections.singletonList(Constant.STATUS_ACTIVE))
                .fieldType(FieldType.STRING)
                .operator(Operator.EQUAL)
                .build());
        request.setFilterRequest(list);
    }
}
