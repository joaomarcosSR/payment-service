package com.payment.service.infrastructure.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleException(
            final Exception ex
    ) {
        return ResponseEntity.internalServerError()
                .body(ApiError.from(ex));
    }

    record ApiError(String message) {
        static ApiError from(final Exception ex) {
            return new ApiError(ex.getMessage());
        }
    }
}
