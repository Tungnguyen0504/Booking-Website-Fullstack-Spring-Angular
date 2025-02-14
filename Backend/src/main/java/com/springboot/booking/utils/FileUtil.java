package com.springboot.booking.utils;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@UtilityClass
@Log4j2
public class FileUtil {
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
