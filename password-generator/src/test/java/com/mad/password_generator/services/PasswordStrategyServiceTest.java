package com.mad.password_generator.services;

import com.mad.password_generator.dto.PasswordStrategyResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PasswordStrategyServiceTest {

    @DisplayName("Should return all password strategy DTOs")
    @Test
    void testGetAllStrategies_shouldReturnAllStrategies() {
        PasswordStrategyService service = new PasswordStrategyService();

        List<PasswordStrategyResponseDTO> strategies = service.getAllStrategies();

        assertFalse(strategies.isEmpty());
        assertTrue(strategies
                .stream()
                //.anyMatch(s -> s.getName().equals("RANDOM")));
                .anyMatch(s -> s.getName().equals("PATTERN")));
    }
}
