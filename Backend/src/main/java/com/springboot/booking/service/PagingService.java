package com.springboot.booking.service;

import com.springboot.booking.common.Constant;
import com.springboot.booking.common.Util;
import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.common.paging.FilterRequest;
import com.springboot.booking.model.FieldType;
import com.springboot.booking.model.Operator;
import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.Room;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
        List<Sort.Order> orders = request.getSortRequest()
                .stream()
                .map(req -> req.getDirection().build(req.getKey()))
                .collect(Collectors.toList());
        return Sort.by(orders);
    }

    public Sort.Order sortByModifiedAt() {
        return Sort.Order.desc("modifiedAt");
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

//    public Specification<Accommodation> withFullTextSearch() {
//
//    }

//    public Specification<Accommodation> sortByModifiedAt() {
//        return (root, query, criteriaBuilder) -> {
//            query.orderBy(Sort.Order.desc("modifiedAt"));
//            return null;
//        };
//    }

    public Specification<Room> sortByRoomPrice(String direction) {
        return switch (direction) {
            case "ASC" -> (root, query, criteriaBuilder) -> {
                query.orderBy(criteriaBuilder.asc(criteriaBuilder.quot(
                        criteriaBuilder.prod(Util.getPath(root, "rooms.price"),
                                criteriaBuilder.diff(100, Util.getPath(root, "rooms.discountPercent"))
                        ), 100)
                ));
                return null;
            };
            case "DESC" -> (root, query, criteriaBuilder) -> {
                query.orderBy(criteriaBuilder.desc(criteriaBuilder.quot(
                        criteriaBuilder.prod(Util.getPath(root, "price"),
                                criteriaBuilder.diff(100, Util.getPath(root, "discountPercent"))
                        ), 100)
                ));
                return null;
            };
            default -> throw new InvalidParameterException();
        };
    }
}
