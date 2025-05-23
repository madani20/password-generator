package com.mad.password_generator.exceptions;

public class ApplicationException extends Exception {

    protected ApplicationException(){}

    public ApplicationException(String message) {
        super(message);
    }
}
