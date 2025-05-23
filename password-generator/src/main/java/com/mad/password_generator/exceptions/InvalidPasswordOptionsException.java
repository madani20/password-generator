package com.mad.password_generator.exceptions;

public class InvalidPasswordOptionsException extends ApplicationException {
    public InvalidPasswordOptionsException() {
    }

    public InvalidPasswordOptionsException(String message) {
        super(message);
    }
}
