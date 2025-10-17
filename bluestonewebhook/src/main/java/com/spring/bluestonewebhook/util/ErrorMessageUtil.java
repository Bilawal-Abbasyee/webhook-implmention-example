package com.spring.bluestonewebhook.util;

public class ErrorMessageUtil {

    public static final String INVALID_WEBHOOK_PAYLOAD = "Invalid webhook payload received.";
    public static final String USER_ACTIVITY_MISSING_FIELDS = "User activity data is incomplete.";
    public static final String USER_REGISTRATION_MISSING_FIELDS = "User registration data is incomplete.";
    public static final String UNAUTHORIZED_ACCESS = "Unauthorized access attempt detected.";

    private ErrorMessageUtil() {
        // Prevent instantiation
    }
}
