package com.springboot.booking.common;

import jakarta.servlet.http.HttpServletRequest;

public class AbstractConstant {

    public static final String MAIL_DETAIL_SUBJECT = "Thepalatin.com";
    public static final String SESSION_TOKEN = "accessToken";
    public static final String SESSION_USER = "user";
    public static final String PATH_V1 = "/rest/api/v1";
    public static final String PATH_AUTH = "/auth";
    public static final String PATH_ADMIN = "/admin";
    public static final String PATH_USER = "/user";
    public static final String PORT_FE_AUTHENTICATION = "5100";
    public static final String PORT_FE_USER = "5200";
    public static final String PORT_FE_ADMIN = "5300";
    public static final String PASSWORD_USER = "User123@";
    public static final String PASSWORD_ADMIN = "Admin123@";

    //file upload
    public static final String FILE_PREFIX_PROVINCE = "province";
    public static final String FILE_PREFIX_ACCOMMODATION = "accommodation";
    public static final String FILE_PREFIX_ACCOMMODATION_TYPE = "accommodation-type";
    public static final String FILE_PREFIX_ROOM = "room";
    public static final String FILE_PREFIX_USER = "user";
    public static final String _PREFIX_USER = "user";
    public static final String FILE_UPLOAD_ROOT = "uploads";

    //property config
    public static final String PROPERTY_SPECIAL_AROUND = "special_around";
    public static final String PROPERTY_DINNING_ROOM = "dinning_room";
    public static final String PROPERTY_BATH_ROOM = "bath_room";
    public static final String PROPERTY_ROOM_SERVICE = "room_service";
    public static final String PROPERTY_VIEW = "view";

    public static final String TIMESTAMP_ddMMyyyyHHmmss = "ddMMyyyyHHmmss";
    public static final String DATETIME_ddMMyyyyHHmmss = "dd-MM-yyyy HH:mm:ss";
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";

    public static String getMsgBodySimple(String code) {
        return "Mã xác thực Thepalatin.com của bạn là: " + code;
    }
}
