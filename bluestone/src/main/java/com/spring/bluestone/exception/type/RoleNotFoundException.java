package com.spring.bluestone.exception.type;


import com.spring.bluestone.util.ErrorCodeUtil;

public class RoleNotFoundException extends RuntimeException {
    private final String errorCode = ErrorCodeUtil.ROLE_NOT_FOUND;

    public RoleNotFoundException(String message) {
        super(message);
    }

    public String getErrorCode() {
        return errorCode;
    }
}