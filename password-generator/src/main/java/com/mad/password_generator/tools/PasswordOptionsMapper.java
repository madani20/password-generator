package com.mad.password_generator.tools;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.dto.PasswordStrengthResponseDTO;
import com.mad.password_generator.models.PasswordOptions;

public interface PasswordOptionsMapper {
    PasswordOptions fromRequest(PasswordOptionsRequestDTO dto);
    PasswordOptionsResponseDTO toResponseDTO(String generatedPassword);

}
