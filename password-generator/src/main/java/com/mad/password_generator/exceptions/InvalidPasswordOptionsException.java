package com.mad.password_generator.exceptions;

import org.springframework.stereotype.Component;


public class InvalidPasswordOptionsException extends ApplicationException {
    public InvalidPasswordOptionsException() {
    }

    public InvalidPasswordOptionsException(String message) {
        super(message);
    }
}
