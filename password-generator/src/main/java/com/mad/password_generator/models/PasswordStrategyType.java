package com.mad.password_generator.models;


public enum PasswordStrategyType {
    RANDOM("Génération aléatoire mixée"),
    PATTERN("Génération basée sur un motif"),
    PIN("Génération aléatoire d'un code numérique"),
    PASS_PHRASE("Génération via une phrase mémorable");


    private final String description;

    PasswordStrategyType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
