package com.mad.password_generator.exceptions;

import com.mad.password_generator.models.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler { //extends ResponseEntityExceptionHandler {

    /**
     *Gestion des exceptions //Cette méthode permet de récupérer proprement les erreurs de @Valid sur le @RequestBody
     *
     * @param ex
     * @param request
     * @return  ResponseEntity
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                errors,
                "Validation Failed",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.badRequest().body(errorResponse);
    }


    /**
     * Gestion des exceptions invalidPasswordOptionsException
     *
     * @param invalidPasswordOptionsException
     * @return
     */
    @ExceptionHandler(InvalidPasswordOptionsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordOptionsException(InvalidPasswordOptionsException invalidPasswordOptionsException,
                                                                               HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                invalidPasswordOptionsException.getMessage(),
                "Invalid password options",
                request.getRequestURI(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(400));
    }



    /**
     * Gestion des exceptions génériques.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred.",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }


}
