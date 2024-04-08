package com.springboot.booking.common;

public class Constant {

    public static final String MAIL_DETAIL_SUBJECT = "Thepalatin.com";

    public static final String PATH_V1 = "/rest/api/v1";
    public static final String PATH_AUTH = "/auth";
    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_USER = "/user";

    //file upload
    public static final String FILE_PREFIX_PROVINCE = "province";
    public static final String FILE_PREFIX_ACCOMMODATION = "accommodation";
    public static final String FILE_PREFIX_ACCOMMODATION_TYPE = "accommodation-type";
    public static final String FILE_PREFIX_ROOM = "room";
    public static final String FILE_PREFIX_USER = "user";
    public static final String FILE_UPLOAD_ROOT = "uploads";

    //date time
    public static final String DATETIME_FORMAT1 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIME_FORMAT2 = "dd-MM-yyyy HH:mm:ss";
    public static final String DATE_FORMAT1 = "yyyy-MM-dd";

    //status
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    public static String getMsgBodySimple(String code) {
        return "Mã xác thực Thepalatin.com của bạn là: " + code;
    }
}
