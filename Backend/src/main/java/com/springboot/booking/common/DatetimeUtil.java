package com.springboot.booking.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DatetimeUtil {

    public static String format_HHmm(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(localTime);
    }

    public static String ofPatternDateTime(LocalDateTime localDateTime, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(localDateTime);
    }

    public static LocalDate parseDateDefault(String value) {
        return LocalDate.parse(value, DateTimeFormatter.ofPattern(Constant.DATE_FORMAT1));
    }

    public static LocalDateTime parseDateTimeDefault(String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ofPattern(Constant.DATETIME_FORMAT1));
    }

    public static int subLocalDate(String val1, String val2) {
        return Period.between(parseDateDefault(val1), parseDateDefault(val2)).getDays();
    }
}
