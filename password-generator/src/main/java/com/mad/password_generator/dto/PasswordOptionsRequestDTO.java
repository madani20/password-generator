package com.mad.password_generator.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordOptionsRequestDTO {

    @Min(value = 6, message = "La longueur minimale est de 4 caractères.")
    @Max(value = 128, message = "La longueur maximale est de 120 caractères")
    @NotNull
    private int length;

    @NotNull
    private boolean includeUppercase = true;

    @NotNull
    private boolean includeLowercase = true;

    @NotNull
    private boolean includeDigits = true;

    @NotNull
    private boolean includeSpecialChars = false;

    private boolean excludeSimilarChars = false;

    private boolean requireEachType = false;

    @NotNull
    private String strategy;

    private String prefix;

    private String suffix;


}
