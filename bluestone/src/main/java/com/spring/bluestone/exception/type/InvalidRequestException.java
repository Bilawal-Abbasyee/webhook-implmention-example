package com.spring.bluestone.exception.type;

import com.spring.bluestone.util.ErrorCodeUtil;
import lombok.Getter;

@Getter
public class InvalidRequestException extends RuntimeException {
    private final String errorCode = ErrorCodeUtil.INVALID_REQUEST;

    public InvalidRequestException(String message) {
        super(message);
    }

}