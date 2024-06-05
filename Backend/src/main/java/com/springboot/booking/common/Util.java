package com.springboot.booking.common;

import com.springboot.booking.model.entity.File;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class Util {
    public static String generateVerificationCode() {
        Random random = new Random();
        int number = random.nextInt(999999);

        return String.format("%06d", number);
    }

    public static String extractTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            Annotation annotation = entityClass.getAnnotation(Table.class);
            try {
                Method method = annotation.annotationType().getMethod("name");
                Object tableName = method.invoke(annotation);
                if (tableName instanceof String) {
                    return (String) tableName;
                }
            } catch (Exception e) {
                log.debug("Something went wrong: {} ", e.getMessage());
            }
        }
        return "";
    }

    public static final String getBasicAuth(String username, String password) {
        String valueToEncode = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
    }

    public static <T, V> Path<V> getPath(Root<T> root, String key) {
        String[] keyArr = key.split("\\.");
        AtomicReference<Path<V>> path = new AtomicReference<>(root.get(keyArr[0]));
        if (keyArr.length > 1) {
            Arrays.asList(keyArr)
                    .subList(1, keyArr.length)
                    .forEach(p -> path.set(path.get().get(p)));
        }
        return path.get();
    }

    public static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf('/') + 1);
    }
}
