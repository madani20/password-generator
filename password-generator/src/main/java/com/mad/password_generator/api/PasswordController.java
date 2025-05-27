package com.mad.password_generator.api;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.services.PasswordOptionsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PasswordController {
    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    private final PasswordOptionsService passwordOptionsService;

    public PasswordController(PasswordOptionsService passwordOptionsService) {
        this.passwordOptionsService = passwordOptionsService;
    }

    @PostMapping(value = "/password/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PasswordOptionsResponseDTO> generate(@RequestBody @Valid PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        logger.info("Init PasswordController()");
        PasswordOptionsResponseDTO generatedPassword = passwordOptionsService.generate(passwordOptionsRequestDTO);
        logger.info("Fin PasswordController()");
        return new ResponseEntity<>(generatedPassword, HttpStatus.CREATED);
    }
    @GetMapping(value = "/test", produces = "text/plain")
    public String getTest() {
        return "Hello test.";
    }

}





// GET /api/strategies    Retourne la liste des stratégies disponibles.
// GET /api/password/strength?value=...   Analyse la force d’un mot de passe donné.
