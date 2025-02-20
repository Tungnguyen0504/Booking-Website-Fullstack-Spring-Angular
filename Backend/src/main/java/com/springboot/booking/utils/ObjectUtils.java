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

@UtilityClass
@Log4j2
public class ObjectUtils {
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
        return val.toLowerCase();
      }
    }
    return "";
  }
}
