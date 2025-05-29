package com.mad.password_generator.exceptions;

import com.mad.password_generator.models.ErrorResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidPasswordOptionsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordOptionsException(InvalidPasswordOptionsException invalidPasswordOptionsException) {
         ErrorResponse errorResponse= new ErrorResponse(invalidPasswordOptionsException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }

    @ExceptionHandler(StrategyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStrategyNotFoundException(StrategyNotFoundException strategyNotFoundException) {
        ErrorResponse errorResponse= new ErrorResponse(strategyNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }

}
