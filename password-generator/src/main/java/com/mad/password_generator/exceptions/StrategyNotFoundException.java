package com.mad.password_generator.exceptions;

public class StrategyNotFoundException extends ApplicationException {
    public StrategyNotFoundException() {    }

    public StrategyNotFoundException(String message) {
        super(message);
    }
}
