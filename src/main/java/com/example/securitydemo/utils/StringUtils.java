package com.example.securitydemo.utils;

public class StringUtils {

    public static String safeToString(Object object) {
        return object == null ? "" : object.toString();
    }

    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean hasText(String str) {
        return !isNullOrEmpty(str);
    }
}
