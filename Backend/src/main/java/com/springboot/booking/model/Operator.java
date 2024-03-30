package com.springboot.booking.model;

import jakarta.persistence.criteria.Path;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public enum Operator {

    OR {
        public <T> Specification<T> build(String key, List<Object> values) {
            String[] keyArr = key.split("\\.");
            return values.stream()
                    .map(value -> (Specification<T>) (root, query, criteriaBuilder) -> {
                                AtomicReference<Path<?>> path = new AtomicReference<>(root.get(keyArr[0]));
                                if (keyArr.length > 1) {
                                    Arrays.asList(keyArr)
                                            .subList(1, keyArr.length)
                                            .forEach(p -> path.set(path.get().get(p)));
                                }
                                return criteriaBuilder.equal(path.get(), value);
                            }
                    )
                    .reduce(Specification::or)
                    .orElse(null);
        }
    };

    public abstract <T> Specification<T> build(String key, List<Object> values);
//    EQUAL {
//        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
//            Object value = request.getFieldType().parse(request.getValue().toString());
//            String[] keys = request.getKey().split("\\.");
//            Path<?> key = root.get(keys[0]);
//            if(keys.length > 1) {
//                for(int i = 1; i < keys.length; ++i) {
//                    key = key.get(keys[i]);
//                }
//            }
//
//            return cb.and(cb.equal(key, value), predicate);
//        }
//    },
//
//    NOT_EQUAL {
//        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
//            Object value = request.getFieldType().parse(request.getValue().toString());
//            String[] keys = request.getKey().split("\\.");
//            Path<?> key = root.get(keys[0]);
//            if(keys.length > 1) {
//                for(int i = 1; i < keys.length; ++i) {
//                    key = key.get(keys[i]);
//                }
//            }
//
//            return cb.and(cb.notEqual(key, value), predicate);
//        }
//    },
//
//    LIKE {
//        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
//            String[] keys = request.getKey().split("\\.");
//            Path<String> key = root.get(keys[0]);
//            if(keys.length > 1) {
//                for(int i = 1; i < keys.length; ++i) {
//                    key = key.get(keys[i]);
//                }
//            }
//
//            return cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toUpperCase() + "%"), predicate);
//        }
//    },
//
//    IN {
//        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
//            List<Object> values = request.getValues();
//            String[] keys = request.getKey().split("\\.");
//            Path<?> key = root.get(keys[0]);
//            if(keys.length > 1) {
//                for(int i = 1; i < keys.length; ++i) {
//                    key = key.get(keys[i]);
//                }
//            }
//
//            CriteriaBuilder.In<Object> inClause = cb.in(key);
//
//            for (Object value : values) {
//                inClause.value(request.getFieldType().parse(value.toString()));
//            }
//            return cb.and(inClause, predicate);
//        }
//    },
//
//    BETWEEN {
//        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
//            Object value = request.getFieldType().parse(request.getValue().toString());
//            Object valueTo = request.getFieldType().parse(request.getValueTo().toString());
//            if (request.getFieldType() != FieldType.CHAR && request.getFieldType() != FieldType.BOOLEAN) {
//                String[] keys = request.getKey().split("\\.");
//                Path<Number> key = root.get(keys[0]);
//                if(keys.length > 1) {
//                    for(int i = 1; i < keys.length; ++i) {
//                        key = key.get(keys[i]);
//                    }
//                }
//
//                Number start = (Number) value;
//                Number end = (Number) valueTo;
//                return cb.and(cb.and(cb.ge(key, start), cb.le(key, end)), predicate);
//            }
//
//            log.info("Can not use between for {} field type.", request.getFieldType());
//            return predicate;
//        }
//
//    }
//    ,IS_NOT_NULL {
//        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
//            String[] keys = request.getKey().split("\\.");
//            Path<?> key = root.get(keys[0]);
//            if(keys.length > 1) {
//                for(int i = 1; i < keys.length; ++i) {
//                    key = key.get(keys[i]);
//                }
//            }
//
//            return cb.and(cb.isNotNull(key), predicate);
//        }
//    }
//
////    ,MATCH {
////        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate) {
////            Expression<String> key = root.get(request.getKey1());
////            Expression<Boolean> expr = cb.function("match", Double.class, );
//////            return cb.and(cb.like(cb.upper(key), "%" + request.getValue().toString().toLowerCase() + "%"), predicate);
////            return cb.and(cb., predicate);
////        }
////    }
//    ;
//
//    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb, FilterRequest request, Predicate predicate);

}
