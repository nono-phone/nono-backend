package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.dto.ErrorResponse;
import com.vn.aptech.smartphone.exception.ConflictException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<ErrorResponse> handleException(Exception e, HttpStatus httpStatus){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(httpStatus, e.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        return handleException(e, HttpStatus.CONFLICT);
    }

}
