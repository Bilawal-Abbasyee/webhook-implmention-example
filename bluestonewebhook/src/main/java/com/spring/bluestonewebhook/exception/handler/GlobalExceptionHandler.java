package com.spring.bluestonewebhook.exception.handler;

import com.spring.bluestonewebhook.exception.model.ErrorResponse;
import com.spring.bluestonewebhook.exception.type.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequestException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), ex.getErrorCode(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), "ERR_INTERNAL_500", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, String code, HttpStatus status, WebRequest request) {
        log.error("Exception: {}, Code: {}, Status: {}", message, code, status);
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), status.value(), code, message, request.getDescription(false));
        return new ResponseEntity<>(error, status);
    }
}

