package com.springboot.booking.utils;

import jakarta.persistence.Table;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Log4j2
@UtilityClass
public class Util {
  public static final Random RANDOM = new Random();

  public static String generateVerificationCode() {
    return String.format("%06d", RANDOM.nextInt(999999));
  }

  @SneakyThrows
  public static String extractTableName(Class<?> entityClass) {
    if (entityClass.isAnnotationPresent(Table.class)) {
      Annotation annotation = entityClass.getAnnotation(Table.class);
      Method method = annotation.annotationType().getMethod("name");
      Object tableName = method.invoke(annotation);
      if (tableName instanceof String val) {
        return val;
      }
    }
    return "";
  }

  public static String getBasicAuth(String username, String password) {
    String valueToEncode = username + ":" + password;
    return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
  }

  public static <T, V> Path<V> getPath(Root<T> root, String key) {
    String[] keyArr = key.split("\\.");
    AtomicReference<Path<V>> path = new AtomicReference<>(root.get(keyArr[0]));
    if (keyArr.length > 1) {
      Arrays.asList(keyArr).subList(1, keyArr.length).forEach(p -> path.set(path.get().get(p)));
    }
    return path.get();
  }

  public static String getFileName(String filePath) {
    return filePath.substring(filePath.lastIndexOf('/') + 1);
  }
}
