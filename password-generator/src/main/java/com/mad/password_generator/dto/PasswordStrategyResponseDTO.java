package com.mad.password_generator.dto;

public class PasswordStrategyResponseDTO {

    private String name;
    private String description;

    public PasswordStrategyResponseDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}
