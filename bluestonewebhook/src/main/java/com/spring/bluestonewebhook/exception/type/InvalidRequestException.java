package com.spring.bluestonewebhook.exception.type;

import com.spring.bluestonewebhook.util.ErrorCodeUtil;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final String errorCode = ErrorCodeUtil.INVALID_REQUEST;

    public InvalidRequestException(String message) {
        super(message);
    }

}
