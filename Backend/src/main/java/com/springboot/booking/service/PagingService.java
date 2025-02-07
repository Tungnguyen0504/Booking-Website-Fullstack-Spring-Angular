package com.springboot.booking.service;

import com.springboot.booking.common.paging.BasePagingRequest;
import com.springboot.booking.model.entity.Accommodation;
import com.springboot.booking.model.entity.Room;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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

//    public Specification<Accommodation> withFullTextSearch() {
//
//    }

//    public Specification<Accommodation> sortByModifiedAt() {
//        return (root, query, criteriaBuilder) -> {
//            query.orderBy(Sort.Order.desc("modifiedAt"));
//            return null;
//        };
//    }

    public Specification<Accommodation> sortByRoomPrice(String direction) {
        return switch (direction) {
            case "ASC" -> (root, query, criteriaBuilder) -> {
                query.orderBy(criteriaBuilder.asc(generateExpressionCalculatePrice(root, query, criteriaBuilder)));
                return null;
            };
            case "DESC" -> (root, query, criteriaBuilder) -> {
                query.orderBy(criteriaBuilder.desc(generateExpressionCalculatePrice(root, query, criteriaBuilder)));
                return null;
            };
            default -> throw new InvalidParameterException();
        };
    }

    private <T> Expression<Number> generateExpressionCalculatePrice(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Subquery<Number> subQuery = query.subquery(Number.class);
        Root<Room> roomRoot = subQuery.from(Room.class);
        subQuery.select(criteriaBuilder.quot(
                criteriaBuilder.prod(roomRoot.get("price"),
                        criteriaBuilder.diff(100, roomRoot.get("discountPercent"))
                ), 100));
        subQuery.where(criteriaBuilder.equal(roomRoot.get("accommodation"), root));
        return subQuery.getSelection();
    }
}
