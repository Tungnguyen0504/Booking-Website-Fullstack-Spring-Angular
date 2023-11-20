package com.springboot.booking.common;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DatetimeUtil {

    public static String LocaleTimeHHmm(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(localTime);
    }

    public static String LocaleTimeddMMyyyyHHmmss(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(AbstractConstant.DATETIME_ddMMyyyyHHmmss);
        return formatter.format(localDateTime);
    }
}
