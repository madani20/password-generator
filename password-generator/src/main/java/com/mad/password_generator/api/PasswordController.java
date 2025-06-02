package com.mad.password_generator.api;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.dto.PasswordStrategyResponseDTO;
import com.mad.password_generator.models.ErrorResponse;
import com.mad.password_generator.services.PasswordOptionsService;
import com.mad.password_generator.services.PasswordStrategyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api")
public class PasswordController {
    private static final Logger logger = LoggerFactory.getLogger(PasswordController.class);

    private final PasswordOptionsService passwordOptionsService;
    private final PasswordStrategyService passwordStrategyService;

    public PasswordController(PasswordOptionsService passwordOptionsService, PasswordStrategyService passwordStrategyService) {
        this.passwordOptionsService = passwordOptionsService;
        this.passwordStrategyService = passwordStrategyService;
    }

    /**
     * Endpoint pour la génération d'un mot de passe.
     *
     *
     * @param passwordOptionsRequestDTO contient les options disponibles pour la génération du mot de passe.
     * @return Le mot de passe.
     */
    @Operation(
            summary = "Generate a secure password",
            description = "Generates a password based on the strategy chosen from the 4 available. " +
                    "Each strategy applies different complexity rules (see strategy documentation).",
            operationId = "generateSecurePassword"
    )
    @ApiResponses(value = {
            @ApiResponse(
                         responseCode = "201",
                         description = "Password generated successfully.",
                         content = @Content(
                                 mediaType = "application/json",
                                 schema = @Schema(implementation = PasswordOptionsResponseDTO.class),
                                 examples = @ExampleObject(
                                         value = "{\"password\": \"A1!cD3\"}"
                         ))),
            @ApiResponse(
                         responseCode = "400",
                         description = "Invalid parameter(s) or unknown strategy.",
                         content = @Content(
                                 schema = @Schema(implementation = ErrorResponse.class),
                                 examples = @ExampleObject(
                                         value = "{\"error\": \"Invalid strategy\"}"))),
            @ApiResponse(
                         responseCode = "500",
                         description = "Internal Server Error",
                         content = @Content(
                                 schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "/password/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PasswordOptionsResponseDTO> generate(@RequestBody @Valid  PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        logger.info("Init generate from PasswordController()");
        PasswordOptionsResponseDTO generatedPassword = passwordOptionsService.generate(passwordOptionsRequestDTO);
        logger.info("Fin generate from PasswordController()");
        return new ResponseEntity<>(generatedPassword, HttpStatus.CREATED);
    }


    /**
     * Endpoint qui renvoie la liste des stratégies disponibles.
     *
     *
     * @return liste des stratégies
     */
    @Operation(
            summary = "Returns the list of existing strategies.",
            description = "Returns the list of existing strategies to choose from for password generation",
            operationId = "listOfStrategies"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of available strategies.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PasswordStrategyResponseDTO.class),
                            examples = @ExampleObject(
                                    value = "{\"name\": \"RANDOM\", \"description\": \"Génération aléatoire mixé\"}"
                            ))),
           @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "/strategies")
    public ResponseEntity<List<PasswordStrategyResponseDTO>> getStrategies() {
        logger.info("Init getStrategies from passwordController");
        List<PasswordStrategyResponseDTO> strategies = passwordStrategyService.getAllStrategies();
        logger.info("Fin getStrategies from passwordController");
        return ResponseEntity.ok(strategies);
    }
  }

// GET /api/password/strength?value=...   Analyse la force d’un mot de passe donné.
