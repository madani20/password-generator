package com.mad.password_generator.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PasswordOptionsRequestDTO {

    @Min(value = 4, message = "La longueur minimale est de 4 caractères.")
    @Max(value = 64, message = "La longueur maximale est de 64 caractères")
    private int length;

    private boolean includeUppercase = true;
    private boolean includeLowercase = true;
    private boolean includeDigits = true;
    private boolean includeSpecialChars = false;
    private boolean excludeSimilarChars = false;

    @NotNull
    private String strategy;

    private String prefix;

    private String suffix;

}
