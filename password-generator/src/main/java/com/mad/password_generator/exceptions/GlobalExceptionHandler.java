package com.mad.password_generator.exceptions;

import com.mad.password_generator.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Gestion des exceptions invalidPasswordOptionsException
     *
     * @param invalidPasswordOptionsException
     * @return
     */
    @ExceptionHandler(InvalidPasswordOptionsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordOptionsException(InvalidPasswordOptionsException invalidPasswordOptionsException) {
         ErrorResponse errorResponse= new ErrorResponse(invalidPasswordOptionsException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }

    /**
     * Gestion des exceptions strategyNotFoundException
     *
     * @param strategyNotFoundException
     * @return
     */
    @ExceptionHandler(StrategyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStrategyNotFoundException(StrategyNotFoundException strategyNotFoundException) {
        ErrorResponse errorResponse= new ErrorResponse(strategyNotFoundException.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }
    /**
     * Gestion des exceptions génériques.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse errorResponse= new ErrorResponse(exception.getMessage());
        return new ResponseEntity<ErrorResponse>(errorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
