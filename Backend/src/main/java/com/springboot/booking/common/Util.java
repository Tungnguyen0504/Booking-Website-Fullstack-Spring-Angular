package com.springboot.booking.common;

import com.springboot.booking.exeption.GlobalException;
import jakarta.persistence.Table;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Random;

@Log4j2
public class Util {

    public static String getBaseUrl(HttpServletRequest request) {
        String baseUrl =
                request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl;
    }

    public static String generateVerificationCode() {
        Random random = new Random();
        int number = random.nextInt(999999);

        return String.format("%06d", number);
    }

    public static String extractTableName(Class<?> entityClass) {
        if(entityClass.isAnnotationPresent(Table.class)) {
            Annotation annotation = entityClass.getAnnotation(Table.class);
            try {
                Method method = annotation.annotationType().getMethod("name");
                Object tableName = method.invoke(annotation);
                if(tableName instanceof String) {
                    return (String) tableName;
                }
            } catch (Exception e) {
                log.debug("Something went wrong: {} ", e.getMessage());
            }
        }
        return "";
    }

    public static String stringFormat(String str) {
        if(StringUtils.isEmpty(str)) {
            throw new GlobalException(ExceptionResult.PARAMETER_INVALID);
        }
        return str.trim();
    }
}
