package com.mad.password_generator.tools;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.models.PasswordOptions;
import org.springframework.stereotype.Component;

@Component
public class PasswordOptionsMapperImpl implements PasswordOptionsMapper{
    @Override
    public PasswordOptions fromRequest(PasswordOptionsRequestDTO dto) {
        return PasswordOptions.builder()
                .length(dto.getLength())
                .includeUppercase(dto.isIncludeUppercase())
                .includeLowercase(dto.isIncludeLowercase())
                .includeDigits(dto.isIncludeDigits())
                .includeSpecialChars(dto.isIncludeSpecialChars())
                .excludeSimilarChars(dto.isExcludeSimilarChars())
                .strategy(dto.getStrategy())
                .pattern(dto.getPattern())
                .prefix(dto.getPrefix())
                .suffix(dto.getSuffix())
                .allowedChars(dto.getAllowedChars())
                //.requireEachType(dto.isRequireEachType())
                //.excludeAmbiguousChars(dto.isExcludeAmbiguousChars())
                //.allowRepeats(dto.isAllowRepeats())
                //.avoidWords(dto.getAvoidWords())
                .build();
    }

    @Override
    public PasswordOptionsResponseDTO toResponseDTO(String generatedPassword) {
       return new PasswordOptionsResponseDTO(generatedPassword);
    }
}
