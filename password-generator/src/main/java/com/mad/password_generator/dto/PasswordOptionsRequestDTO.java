package com.mad.password_generator.dto;

import com.mad.password_generator.models.PasswordStrategyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordOptionsRequestDTO {

    @Schema(description = "Password length (min: 6, max: 128)", example = "12", minimum = "6", maximum = "128")
    @Min(value = 6, message = "La longueur minimale est de 6 caractères.")
    @Max(value = 128, message = "La longueur maximale est de 128 caractères")
    private int length;

    @Schema(description = "Use uppercase letters", example = "true")
    private boolean includeUppercase = true;

    @Schema(description = "Use lowercase letters", example = "false")
    private boolean includeLowercase = true;

    @Schema(description = "Use digits ", example = "true")
    private boolean includeDigits = true;

    @Schema(description = "Use special character '-' ", example = "true")
    private boolean includeDash = true;

    @Schema(description = "Use special chars ", example = "true")
    private boolean includeSpecialChars = false;

    @Schema(description = "Exclude similar chars ", example = "false")
    private boolean excludeSimilarChars = false;

    @Schema(description = "Require all types", example = "true")
    private boolean requireEachType = false;

    @Schema(description = "Name of the strategy used\", example = \"RANDOM\", allowableValues = {\"RANDOM\", \"PATTERN\", \"PIN\", \"PASS_PHRASE\"})")
    @NotNull
    private PasswordStrategyType strategy;

    @Schema(description = "Pattern to be respected (optional, for PATTERN strategy)\", example = \"##-DDDDLL-#L\"")
    private String pattern;

    @Schema(description = "A prefix is added at the beginning of the password", example = "A123")
    private String prefix;

    @Schema(description = "A suffix is added to the end of the password", example = "aBc456" )
    private String suffix;


}
