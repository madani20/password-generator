package com.mad.password_generator.models;


public enum PasswordStrategyType {
    RANDOM("Mixed random generation"),
    PATTERN("Pattern-based generation"),
    PIN("Random generation of a digital code"),
    PASS_PHRASE("Generation via a memorable phrase");


    private final String description;

    PasswordStrategyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
