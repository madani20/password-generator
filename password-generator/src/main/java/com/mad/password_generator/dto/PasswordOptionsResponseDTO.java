package com.mad.password_generator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordOptionsResponseDTO {

    private String password;

    public PasswordOptionsResponseDTO(String password) {
        this.password = password;
    }
}
