package com.springboot.booking.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConst {
  public static final String UPLOAD_PATH =
      MessageFormat.format("/{0}/{1}", System.getProperty("user.dir"), "uploads");
  public static final String PATH_PROVINCE = "province";
  public static final String PATH_ACCOMMODATION = "accommodation";
  public static final String PATH_ACCOMMODATION_TYPE = "accommodation-type";
  public static final String PATH_ROOM = "room";
  public static final String PATH_USER = "user";
}
