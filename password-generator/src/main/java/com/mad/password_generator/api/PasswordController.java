package com.mad.password_generator.api;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.services.PasswordOptionsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/api")
public class PasswordController {

    private final PasswordOptionsService passwordGenerationService;

    public PasswordController(PasswordOptionsService passwordGenerationService) {
        this.passwordGenerationService = passwordGenerationService;
    }

    @PostMapping("/password/generate") //Génère un mot de passe selon les critères fournis dans le corps de la requête
    /**
     * exemple de requête avec payload:
     * POST /api/password/generate: {
     *   "length": 16,
     *   "includeUppercase": true,
     *   "includeLowercase": true,
     *   "includeDigits": true,
     *   "includeSpecialChars": false,
     *   "strategy": "RANDOM"
     * }
     */
    public ResponseEntity<PasswordOptionsResponseDTO> generate(@Valid @RequestBody PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        PasswordOptionsResponseDTO generatedPassword = passwordGenerationService.generate(passwordOptionsRequestDTO);
        return new ResponseEntity<>(generatedPassword, HttpStatus.CREATED);
    }


    // GET /api/strategies    Retourne la liste des stratégies disponibles.
    // GET /api/password/strength?value=...   Analyse la force d’un mot de passe donné.
}
