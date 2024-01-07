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
    public static final String FILE_PREFIX_PROVINCE = "province";
    public static final String FILE_PREFIX_ACCOMMODATION = "accommodation";
    public static final String FILE_PREFIX_ACCOMMODATION_TYPE = "accommodation-type";
    public static final String FILE_PREFIX_ROOM = "room";
    public static final String FILE_PREFIX_USER = "user";
    public static final String FILE_UPLOAD_ROOT = "uploads";
    public static final String TIMESTAMP_ddMMyyyyHHmmss = "ddMMyyyyHHmmss";
    public static final String DATETIME_ddMMyyyyHHmmss = "dd-MM-yyyy HH:mm:ss";

    public static String getMsgBodySimple(String code) {
        return "Mã xác thực Thepalatin.com của bạn là: " + code;
    }

    public static String getMsgBody(HttpServletRequest request, String recipient, String homeLink) {
        String url = Util.getBaseUrl(request) + PATH_V1 + PATH_AUTH + "/verification/" + recipient;
        return "        <h2>Verify and sign in</h2>\n" +
                "        Hi there,\n" +
                "        <br><br>\n" +
                "        Verify yourself below to sign in to your Thepalatin.com account for <strong style=\"text-decoration: underline;\">"
                + recipient + "</strong>.\n" +
                "        <br>\n" +
                "        The link can only be used once and expires in 10 minutes if you don’t use it.\n" +
                "        <br>\n" +
                "        <input type=\"hidden\" name=\"email\" value=\"" + recipient + "\">" +
                "        <a href=\"" + url + "\" style=\"color: blue;\">Verify me.</a>\n";
    }
}
