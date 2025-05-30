package com.mad.password_generator.dto;

import com.mad.password_generator.models.PasswordStrategyType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordOptionsRequestDTO {

    //@Min(value = 6, message = "La longueur minimale est de 6 caractères.")
    //@Max(value = 128, message = "La longueur maximale est de 128 caractères")
    private int length;


    private boolean includeUppercase = true;

    private boolean includeLowercase = true;

    private boolean includeDigits = true;

    private boolean includeDash = true;

    private boolean includeSpecialChars = false;

    private boolean excludeSimilarChars = false;

    private boolean requireEachType = false;

    @NotNull
    private PasswordStrategyType strategy;

    private String pattern;

    private String prefix;

    private String suffix;


}
