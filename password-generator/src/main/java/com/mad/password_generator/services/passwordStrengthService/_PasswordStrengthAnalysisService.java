package com.mad.password_generator.services.passwordStrengthService;

import com.mad.password_generator.dto.PasswordStrengthRequestDTO;
import com.mad.password_generator.dto.PasswordStrengthResponseDTO;

public interface _PasswordStrengthAnalysisService {

    public PasswordStrengthResponseDTO analyze(PasswordStrengthRequestDTO password);


}
