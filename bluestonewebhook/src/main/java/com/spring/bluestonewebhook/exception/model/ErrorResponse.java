package com.spring.bluestonewebhook.exception.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String errorCode;
    private String message;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String errorCode, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
        this.path = path;
    }
}