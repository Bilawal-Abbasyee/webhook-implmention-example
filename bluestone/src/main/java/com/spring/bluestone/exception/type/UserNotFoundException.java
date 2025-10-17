package com.spring.bluestone.exception.type;


import com.spring.bluestone.util.ErrorCodeUtil;

public class UserNotFoundException extends RuntimeException {
    private final String errorCode = ErrorCodeUtil.USER_NOT_FOUND;

    public UserNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}