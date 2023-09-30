package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.dto.ErrorResponse;
import com.vn.aptech.smartphone.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e, WebRequest request) {
        return handleException(e, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e, WebRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Access Denied info: ", authentication);
        return handleException(e, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e, WebRequest request) {
        return handleException(e, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e, WebRequest request) {
        return handleException(e, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException e, WebRequest request) {
        return handleException(e, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleApplicationException(Exception e, WebRequest request) {
        log.error(request.getDescription(true));
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCategoryException(Exception e, WebRequest request) {
        return handleException(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(NotFoundListProductException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundListProductException(Exception e, WebRequest request) {
        return handleException(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleInsufficientStockException(Exception e, WebRequest request) {
        return handleException(e, HttpStatus.BAD_REQUEST, request);
    }

    private ResponseEntity<ErrorResponse> handleException(Exception e, HttpStatus httpStatus, WebRequest webRequest){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                e.getMessage(),
                webRequest.getDescription(false),
                httpStatus
        );
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
