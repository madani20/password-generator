package com.mad.password_generator.api;

import com.mad.password_generator.dto.*;
import com.mad.password_generator.models.ErrorResponse;
import com.mad.password_generator.services.PasswordOptionsService;
import com.mad.password_generator.services.PasswordStrategyService;
import com.mad.password_generator.services.passwordStrengthService.PasswordStrengthEvaluator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    private final PasswordStrengthEvaluator passwordStrengthEvaluator;

    public PasswordController(PasswordOptionsService passwordOptionsService, PasswordStrategyService passwordStrategyService, PasswordStrengthEvaluator passwordStrengthEvaluator) {
        this.passwordOptionsService = passwordOptionsService;
        this.passwordStrategyService = passwordStrategyService;
        this.passwordStrengthEvaluator = passwordStrengthEvaluator;
    }

    /**
     * Endpoint pour la génération d'un mot de passe sécurisé.
     *
     *
     * @param passwordOptionsRequestDTO contient les options disponibles pour la génération du mot de passe.
     * @return Le mot de passe.
     */
    @Tag(name = "Password Generation", description = "Operations related to password generation")
    @Operation(
            summary = "Generate a secure password",
            description = "Generates a password using one of the four available strategies. " +
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
                                 examples = {
                                    @ExampleObject(
                                            name = "Simple random password",
                                            summary = "Alphanumeric characters with uppercase letters and allowedChar",
                                            value = """
                                                    {
                                                      "length": 10,
                                                      "strategy": "RANDOM",
                                                      "allowedChars": "AbcD12!*DK",
                                                      "includeUppercase": true,
                                                      "includeLowercase": true,
                                                      "includeDigits": true
                                                    }"""
                                    ),
                                    @ExampleObject(
                                            name = "Password with special characters",
                                            summary = "Random with special characters and numbers",
                                            value = """
                                                    {
                                                      "length": 16,
                                                      "strategy": "RANDOM",
                                                      "includeSpecialChars": true,
                                                      "includeDigits": true
                                                    }"""
                                    ),
                                    @ExampleObject(
                                            name = "Pattern Based (PATTERN)",
                                            summary = "Password based on a predefined pattern",
                                            value = """
                                                    {
                                                      "length": 12,
                                                      "strategy": "PATTERN",
                                                      "pattern": "DL#-LL-###D"
                                                    }"""
                                    ),@ExampleObject(
                                                 name = "Custom set ",
                                                 summary = "Pattern based on a provided character set",
                                                 value = """
                                                         {
                                                           "length": 20,
                                                           "strategy": "CUSTOM_SET",
                                                           "allowedChars": "8M!tK"
                                                         }"""
                                    ), @ExampleObject(
                                                 name = "Digital code",
                                                 summary = "Generate a digital code",
                                                 value = """
                                                        {
                                                          "length": 6,
                                                          "strategy": "PIN",
                                                          "includeDigits": true
                                                        }"""
                                             )
                                 }
                          )),
            @ApiResponse(
                         responseCode = "400",
                         description = "Bad request - Invalid password options.",
                         content = @Content(
                                 mediaType = "application/json",
                                 schema = @Schema(implementation = ErrorResponse.class),
                                 examples = {
                                         @ExampleObject(
                                                 name = "length less than 6",
                                                 value = "{\"error\": \"The minimum length is 6 characters.\"}"
                                         ),
                                         @ExampleObject(
                                                 name = "Invalid strategy",
                                                 value = "{\n" +
                                                         "  \"status\": 400,\n" +
                                                         "  \"error\": \"Bad Request\",\n" +
                                                         "  \"message\": \"Invalid strategy: 'Custom set'. Allowed values: [PATTERN, RANDOM, CUSTOM_SET, PASS_PHRASE]\",\n" +
                                                         "  \"path\": \"/api/password/generate\",\n" +
                                                         "  \"timestamp\": \"2025-05-21T17:35:10\"\n" +
                                                         "}"
                                         ),
                                         @ExampleObject(
                                                 name = "No character options selected",
                                                 value = """
                                                        {
                                                         "status": 400,
                                                          "error": "Validation Failed",
                                                         "message": "allowedChars: must not be blank",
                                                         "path": "/api/password/generate",
                                                         "timestamp": "2025-05-21T17:45:00"
                                                         }
                                                        """
                                         ),
                                         @ExampleObject(
                                                 name = "Empty char set",
                                                 value = """
                                                        {
                                                         "status": 400,
                                                         "error": "Validation Failed",
                                                         "message": "You must provide a non-empty character set.",
                                                         "path": "/api/password/generate",
                                                         "timestamp": "2025-05-21T17:45:00"
                                                         }
                                                        """
                                         )
                                 } )),
            @ApiResponse(
                         responseCode = "500",
                         description = "Internal Server Error",
                         content = @Content(
                                 schema = @Schema(implementation = ErrorResponse.class),
                                 examples = {
                                         @ExampleObject(
                                                 name = "Generic error",
                                                 value = """
                                                        {
                                                          "status": 500,
                                                          "error": "Internal Server Error",
                                                          "message": "An unexpected error occurred.",
                                                          "path": "/api/password/generate",
                                                          "timestamp": "2025-05-21T17:45:00"
                                                        }
                                                    """
                                         )})),

    })
    @PostMapping(value = "/password/generate", consumes = "application/json", produces = "application/json")
    public ResponseEntity<PasswordOptionsResponseDTO> generate(@RequestBody @Valid  PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        logger.info("Init generate from PasswordController()");
        PasswordOptionsResponseDTO generatedPassword = passwordOptionsService.generate(passwordOptionsRequestDTO);
        logger.info("Fin generate from PasswordController()");
        return new ResponseEntity<>(generatedPassword, HttpStatus.CREATED);
    }





    @Tag(name = "Password Strength Analyze", description = "analyzes the strength of a password.")
    @Operation(
            summary = "Analyze password strength",
            description = "Analyzes the strength of a given password, returns a score (0-10), a quality level and a list of improvement suggestions.",
            operationId = "analyzePasswordStrength"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Score generated successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PasswordStrengthResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Example response",
                                            summary = "Password Analysis Example",
                                            description = "A strong password returning a high score",
                                            value = """
                                                    {
                                                      "score": 7,
                                                      "level": "Bonne",
                                                      "suggestions": []
                                                    }
                                                    """
                                    ),
                                    @ExampleObject(
                                            name = "Another example of a response",
                                            summary = "Example of weak password analysis",
                                            description = "A weak password returning a low score",
                                            value = """
                                                    {
                                                      "score": 3,
                                                      "level": "Faible",
                                                      "suggestions": [
                                                            "Utilisez au moins 12 caractères",
                                                            "Ajoutez des chiffres",
                                                            "Ajoutez des caractères spéciaux"
                                                          ]
                                                    }
                                                    """
                                    )
                            }
                    )),
            @ApiResponse(
                    responseCode = "400",
                    description = "Mot de passe invalide (vide ou nul)",
                    content = @Content
            )
    })
    @PostMapping("/password/strength")
    public ResponseEntity<PasswordStrengthResponseDTO> evaluate(@RequestBody @Valid PasswordStrengthRequestDTO passwordStrengthRequestDTO) {
        logger.info("Init evaluate from PasswordController");

        PasswordStrengthResponseDTO passwordStrengthResponseDTO = passwordStrengthEvaluator.analyze(passwordStrengthRequestDTO);

        logger.info("Fin evaluate from PasswordController");
        return new ResponseEntity<>(passwordStrengthResponseDTO, HttpStatus.CREATED);
    }


    /**
     * Endpoint qui renvoie la liste des stratégies disponibles.
     *
     *
     * @return liste des stratégies
     */
    @Tag(name = "List the strategies", description = "Operations related to available strategies")
    @Operation(
            summary = "Returns the list of existing strategies.",
            description = "Lists all available strategies for password generation",
            operationId = "listOfStrategies"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "List of available strategies.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PasswordStrategyResponseDTO.class),
                            examples = {
                                    @ExampleObject(
                                    name = "List of strategies",
                                            value = """
                                                    [
                                                      {"name": "RANDOM", "description": "Mixed random generation. Generate a purely random password with all allowed characters."},
                                                      {"name": "PATTERN", "description": "Generation based on a defined pattern"},
                                                      {"name": "PIN", "description": "Generate a random PIN code" },
                                                      {"name": "CUSTOM_SET", "description": "Generation with set of possible characters" },
                                                      {"name": "PASS_PHRASE", "description": "Memorable phrase with several words" }
                                                    ]"""
                                    )
                            }
                    )),
           @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Generic error",
                                            value = """
                                                    {
                                                      "status": 500,
                                                      "error": "Internal Server Error",
                                                      "message": "An unexpected error occurred.",
                                                      "path": "/api/strategies",
                                                      "timestamp": "2025-05-21T17:45:00"
                                                    }
                                                    """
                                    )
                            }
                            ))
    }
    )
    @GetMapping(value = "/strategies")
    public ResponseEntity<List<PasswordStrategyResponseDTO>> getStrategies() {
        logger.info("Init getStrategies from passwordController");
        List<PasswordStrategyResponseDTO> strategies = passwordStrategyService.getAllStrategies();
        logger.info("Fin getStrategies from passwordController");
        return ResponseEntity.ok(strategies);
    }
  }


