package com.mad.password_generator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordOptionsResponseDTO {

    @Schema(description = "Generated password ", example = "4k-eaSO3PGMow4blknO*" )
    private String password;

    public PasswordOptionsResponseDTO(String password) {
        this.password = password;
    }
}
