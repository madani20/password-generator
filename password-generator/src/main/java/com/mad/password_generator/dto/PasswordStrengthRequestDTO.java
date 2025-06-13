package com.mad.password_generator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PasswordStrengthRequestDTO {

    @NotNull
    private String password;

    public PasswordStrengthRequestDTO(String password) {
        this.password = password;
    }
}
