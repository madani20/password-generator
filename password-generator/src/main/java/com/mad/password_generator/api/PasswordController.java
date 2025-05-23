package com.mad.password_generator.api;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.services.PasswordGenerationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api")
public class PasswordController {

    private final PasswordGenerationService passwordGenerationService;

    public PasswordController(PasswordGenerationService passwordGenerationService) {
        this.passwordGenerationService = passwordGenerationService;
    }

    @PostMapping("/generate")
    public ResponseEntity<PasswordOptionsResponseDTO> generate(@Valid @RequestBody PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        PasswordOptionsResponseDTO generatedPassword = passwordGenerationService.generate(passwordOptionsRequestDTO);
        return ResponseEntity.ok(generatedPassword);
    }
}
