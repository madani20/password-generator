package com.mad.password_generator.api;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.models.ErrorResponse;
import com.mad.password_generator.services.PasswordOptionsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@Validated
@RestController
@RequestMapping("/api")
public class PasswordController {
    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    private final PasswordOptionsService passwordOptionsService;

    public PasswordController(PasswordOptionsService passwordOptionsService) {
        this.passwordOptionsService = passwordOptionsService;
    }

    /**
     * Endpoint pour la génération d'un mot de passe.
     *
     *
     * @param passwordOptionsRequestDTO contient les options disponibles pour la génération du mot de passe.
     * @return Le mot de passe.
     */
    @Operation(summary = "Generate a password", description = "Allows a user to generate a password according to a chosen strategy")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                         description = "Password generated successfully.",
                         content = @Content(mediaType = "application/json",  schema = @Schema(implementation = PasswordOptionsResponseDTO.class))),
            @ApiResponse(responseCode = "400",
                         description = "Requête invalide ou données manquantes",
                         content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500",
                         description = "Erreur interne du serveur",
                         content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/password/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PasswordOptionsResponseDTO> generate(@RequestBody PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        logger.info("Init PasswordController()");
        PasswordOptionsResponseDTO generatedPassword = passwordOptionsService.generate(passwordOptionsRequestDTO);
        logger.info("Fin PasswordController()");
        return new ResponseEntity<>(generatedPassword, HttpStatus.CREATED);
    }
  }





// GET /api/strategies    Retourne la liste des stratégies disponibles.
// GET /api/password/strength?value=...   Analyse la force d’un mot de passe donné.
