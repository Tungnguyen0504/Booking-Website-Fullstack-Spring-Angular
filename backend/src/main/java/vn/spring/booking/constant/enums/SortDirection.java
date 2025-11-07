package vn.spring.booking.constant.enums;

import org.springframework.data.domain.Sort;

public enum SortDirection {
    ASC {
        public <T> Sort.Order build(String key) {
            return Sort.Order.asc(key);
        }
    }, DESC {
        public <T> Sort.Order build(String key) {
            return Sort.Order.desc(key);
        }
    };

    public abstract <T> Sort.Order build(String key);
}
