package com.springboot.booking.constant.enums;

import com.springboot.booking.common.DatetimeUtil;

public enum FieldType {
    STRING {
        public <T> T parse(String value) {
            return (T) value;
        }
    }, INTEGER {
        public <T> T parse(String value) {
            return (T) Integer.valueOf(value);
        }
    }, DATE {
        public <T> T parse(String value) {
            return (T) DatetimeUtil.parseDateDefault(value);
        }
    }, DATETIME {
        public <T> T parse(String value) {
            return (T) DatetimeUtil.parseDateTimeDefault(value);
        }
    };

    public abstract <T> T parse(String value);
}
