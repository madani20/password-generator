package com.mad.password_generator.models;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;

public enum PasswordStrategyType {
    RANDOM("Mixed random generation"),
    PATTERN("Pattern-based generation"),
    CUSTOM_SET("Generation from a provided set of possible characters"),
    PIN("Random generation of a digital code"),
    PASS_PHRASE("Generation via a memorable phrase");


    private final String description;

    PasswordStrategyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }

    @JsonCreator
    public static PasswordStrategyType fromString(String key) {
        for (PasswordStrategyType strategy : PasswordStrategyType.values()) {
            if (strategy.name().equalsIgnoreCase(key)) {
                return strategy;
            }
        }
        throw new InvalidPasswordOptionsException("Unknown strategy: " + key);
    }
}
