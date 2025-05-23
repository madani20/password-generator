package com.mad.password_generator.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleInvalidPasswordOptionsException(InvalidPasswordOptionsException invalidPasswordOptionsException) {
        return new ResponseEntity<>(invalidPasswordOptionsException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
