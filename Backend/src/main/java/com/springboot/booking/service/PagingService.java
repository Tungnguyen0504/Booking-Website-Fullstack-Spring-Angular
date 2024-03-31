package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.DatetimeUtil;
import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.common.paging.SortRequest;
import com.springboot.booking.exeption.GlobalException;
import com.springboot.booking.model.SortDirection;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PagingService {
    public <T> List<Specification<T>> buildSpecifications(BasePagingRequest request) {
        List<Specification<T>> specifications = new ArrayList<>();
//        request.getFilterRequest().forEach(req -> specifications.add(req.getOperator().build(req.getKey(), req.getValues())));
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

    public static <T> T parse(String value, Class<?> type) {
        if (Integer.class.equals(type)) {
            return (T) Integer.valueOf(value);
        } else if (String.class.equals(type)) {
            return (T) value;
        } else if (LocalDate.class.equals(type)) {
            return (T) DatetimeUtil.parseDate(value, Constant.DATE_FORMAT1);
        } else if (LocalDateTime.class.equals(type)) {
            return (T) DatetimeUtil.parseDateTime(value, Constant.DATETIME_FORMAT2);
        } else {
            throw new GlobalException("Unsupported type: " + type);
        }
    }
}
