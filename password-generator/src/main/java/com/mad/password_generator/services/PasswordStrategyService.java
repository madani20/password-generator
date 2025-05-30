package com.mad.password_generator.services;

import com.mad.password_generator.dto.PasswordStrategyDTO;
import com.mad.password_generator.models.PasswordStrategyType;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PasswordStrategyService {
    public List<PasswordStrategyDTO> getAllStrategies() {
        return Arrays.stream(PasswordStrategyType.values())
                .map(type -> new PasswordStrategyDTO(type.name(), type.getDescription()))
                .collect(Collectors.toList());
    }
}
