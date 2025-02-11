package com.springboot.booking.model;

import com.springboot.booking.common.ExceptionResult;
import com.springboot.booking.utils.ObjectUtils;
import com.springboot.booking.exeption.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
public enum Operator {

    OR {
        public <T, V extends Comparable<? super V>> Specification<T> build(String key, List<V> values) {
            String[] keyArr = key.split("\\.");
            return values.stream()
                    .map(value -> (Specification<T>) (root, query, criteriaBuilder)
                            -> criteriaBuilder.equal(ObjectUtils.getPath(root, key), value)
                    )
                    .reduce(Specification::or)
                    .orElse(null);
        }
    },
    EQUAL {
        public <T, V extends Comparable<? super V>> Specification<T> build(String key, List<V> values) {
            if (CollectionUtils.isEmpty(values) || values.size() != 1) {
                throw new GlobalException(ExceptionResult.PARAMETER_INVALID);
            }
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.equal(ObjectUtils.getPath(root, key), values.get(0));
        }
    },
    BETWEEN {
        public <T, V extends Comparable<? super V>> Specification<T> build(String key, List<V> values) {
            if (CollectionUtils.isEmpty(values) || values.size() != 2) {
                throw new GlobalException(ExceptionResult.PARAMETER_INVALID);
            }
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.between(ObjectUtils.getPath(root, key), values.get(0), values.get(1));
        }
    },
    LIKE {
        public <T, V extends Comparable<? super V>> Specification<T> build(String key, List<V> values) {
            if (CollectionUtils.isEmpty(values) || values.size() != 1) {
                throw new GlobalException(ExceptionResult.PARAMETER_INVALID);
            }
            return (root, query, criteriaBuilder)
                    -> criteriaBuilder.like(ObjectUtils.getPath(root, key), ("%" + values.get(0) + "%"));
        }
    };

    public abstract <T, V extends Comparable<? super V>> Specification<T> build(String key, List<V> values);

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
