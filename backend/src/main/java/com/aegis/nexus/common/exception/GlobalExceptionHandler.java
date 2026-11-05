package com.aegis.nexus.common.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlatformException.class)
    public ResponseEntity<ErrorResponse> handlePlatformException(PlatformException ex) {
        ErrorResponse error = new ErrorResponse(
            Instant.now().toString(),
            ex.getErrorCode(),
            ex.getMessage(),
            ex.getHttpStatus()
        );
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
            Instant.now().toString(),
            "INTERNAL_SERVER_ERROR",
            ex.getMessage(),
            500
        );
        return ResponseEntity.status(500).body(error);
    }

    public record ErrorResponse(String timestamp, String errorCode, String message, int status) {}
}
