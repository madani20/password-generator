package com.mad.password_generator.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "DTO containing score, password, entropy, level, and suggestions")
public class PasswordStrengthResponseDTO {

    private short score;
    private String level;
    private List<String> suggestions;

    public PasswordStrengthResponseDTO(short score, String level, List<String> suggestions) {
        this.score = score;
        this.level = level;
        this.suggestions = suggestions;
    }


    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}


