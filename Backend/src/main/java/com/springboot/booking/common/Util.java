package com.springboot.booking.common;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Random;

public class Util {
    public static String getBaseUrl(HttpServletRequest request) {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return baseUrl;
    }

    private static String generateVerificationCode() {
        Random random = new Random();
        int number = random.nextInt(999999);

        return String.format("%06d", number);
    }
}
