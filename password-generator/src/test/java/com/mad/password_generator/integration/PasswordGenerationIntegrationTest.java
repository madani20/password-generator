package com.mad.password_generator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import com.mad.password_generator.services.PasswordOptionsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.matchesPattern;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PasswordGenerationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("POST /api/password/generate with CUSTOM_SET strategy should return a valid password")
    public void testGeneratePassword_withCustomSetStrategy() throws Exception {
        PasswordOptionsRequestDTO options = new PasswordOptionsRequestDTO();
        options.setStrategy(PasswordStrategyType.CUSTOM_SET);
        options.setLength(12);
        options.setAllowedChars("AB123");

        System.out.println(objectMapper.writeValueAsString(options));

        mockMvc.perform(
                post("/api/password/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(options)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.password").value(matchesPattern("^[AB123!]+$")));
    }


    @Test
    @DisplayName("POST /api/password/generate with CUSTOM_SET strategy and empty allowedChars should return 400")
    public void testGeneratePassword_withEmptyAllowedChars_shouldReturnBadRequest() throws Exception {
        PasswordOptionsRequestDTO options = new PasswordOptionsRequestDTO();
        options.setLength(12);
        options.setStrategy(PasswordStrategyType.CUSTOM_SET);
        options.setAllowedChars(""); // allowedChars est vide

        mockMvc.perform(
                        post("/api/password/generate")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(options)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("You must provide a non-empty character set."));
    }

}

