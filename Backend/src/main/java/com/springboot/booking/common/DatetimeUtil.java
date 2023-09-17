package com.springboot.booking.common;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DatetimeUtil {

    public static String LocaleTimeHHmm(LocalTime localTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return formatter.format(localTime);
    }
}
