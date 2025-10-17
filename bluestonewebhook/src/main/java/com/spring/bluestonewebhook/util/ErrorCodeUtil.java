package com.spring.bluestonewebhook.util;

public class ErrorCodeUtil {

    public static final String WEBHOOK_INVALID = "ERR_WEBHOOK_400";
    public static final String USER_ACTIVITY_INVALID = "ERR_ACTIVITY_400";
    public static final String USER_REGISTRATION_INVALID = "ERR_REGISTRATION_400";
    public static final String UNAUTHORIZED = "ERR_AUTH_401";
    public static final String INVALID_REQUEST = "ERR_REQUEST_400";

    private ErrorCodeUtil() {
        // Prevent instantiation
    }
}
