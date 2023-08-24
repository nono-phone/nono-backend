package com.vn.aptech.smartphone.controller;

import com.vn.aptech.smartphone.dto.ErrorResponse;
import com.vn.aptech.smartphone.exception.NotFoundException;
import com.vn.aptech.smartphone.exception.ConflictException;
import com.vn.aptech.smartphone.exception.LoginFailedException;
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
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.debug("Access Denied info: ", authentication);
        return handleException(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        return handleException(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        return handleException(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginFailedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleLoginFailedException(LoginFailedException e) {
        return handleException(e, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleApplicationException(Exception e, WebRequest request) {
        log.error(request.getDescription(true));
        return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleCategoryException(Exception e, WebRequest request) {
        return handleException(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<ErrorResponse> handleException(Exception e, HttpStatus httpStatus){
        log.error(e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(httpStatus, e.getMessage());
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

}
