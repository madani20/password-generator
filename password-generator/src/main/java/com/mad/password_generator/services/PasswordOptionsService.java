package com.mad.password_generator.services;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import com.mad.password_generator.strategies._PasswordGenerationStrategy;
import com.mad.password_generator.tools.PasswordOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class PasswordOptionsService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordOptionsService.class);

    private final PasswordOptionsMapper mapper;
    private final PasswordPostProcessorChain passwordPostProcessorChain;
    private final PasswordGenerationStrategyRegistry passwordGenerationStrategyRegistry;

    public PasswordOptionsService(PasswordOptionsMapper mapper, PasswordPostProcessorChain postProcessorChain, PasswordGenerationStrategyRegistry passwordGenerationStrategyRegistry) {
        this.mapper = mapper;
        this.passwordPostProcessorChain = postProcessorChain;
        this.passwordGenerationStrategyRegistry = passwordGenerationStrategyRegistry;
    }

    /**
     * Méthode principalle pour la génération du mot de passe.
     *
     * @param passwordOptionsRequestDTO
     * @return Un objet de type 'PasswordOptionsResponseDTO' constituant le mot de passe.
     */
    public PasswordOptionsResponseDTO generate(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        logger.info("Init generate()");

        validateInput(passwordOptionsRequestDTO);

        PasswordOptions options = mapper.fromRequest(passwordOptionsRequestDTO);

        /* Sélection de la stratégie */
        _PasswordGenerationStrategy strategy = passwordGenerationStrategyRegistry.getStrategy(options.getPasswordStrategyType());

        /* Génération du mot de pasee en fonction de la stratégie */
        String password = strategy.generate(options);

        /* Post-traitements le cas échéant */
        String postProcessedPassword = doPostProcessorChain(password, options);

        PasswordOptionsResponseDTO generatedPassword;
        generatedPassword = mapper.toResponseDTO(postProcessedPassword);

        logger.info("Fin generate()");
        return generatedPassword;
    }


    //=============================== METHODES UTILITAIRES =========================================================

    private void validateInput(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        logger.info("Init validateInput");

        if (passwordOptionsRequestDTO == null)
            throw new InvalidPasswordOptionsException("No query object");

        isKnownStrategy(passwordOptionsRequestDTO.getStrategy().toString());


        if (passwordOptionsRequestDTO.getStrategy() == null || passwordOptionsRequestDTO.getStrategy().toString().isBlank())
            throw new InvalidPasswordOptionsException("A generation strategy is required.");

        if (passwordOptionsRequestDTO.getStrategy() != PasswordStrategyType.PATTERN
                && passwordOptionsRequestDTO.getStrategy() != PasswordStrategyType.CUSTOM_SET) {
            if (!passwordOptionsRequestDTO.isIncludeUppercase() && !passwordOptionsRequestDTO.isIncludeLowercase() && !passwordOptionsRequestDTO.isIncludeDigits()
                    && !passwordOptionsRequestDTO.isIncludeSpecialChars() && !passwordOptionsRequestDTO.isIncludeDash()) {
                throw new InvalidPasswordOptionsException("At least one character type must be selected!");
            }
        }
        logger.info("Fin validateInput");
    }

    //============================== UTILITAIRES ======================================================================


    private void isKnownStrategy(String strategy) {
         if (!PasswordStrategyType.RANDOM.toString().equals(strategy.trim()) && !PasswordStrategyType.PATTERN.toString().equals(strategy.trim()) &&
                !PasswordStrategyType.CUSTOM_SET.toString().equals(strategy.trim()) && !PasswordStrategyType.PASS_PHRASE.toString().equals(strategy.trim()) &&
                !PasswordStrategyType.PIN.toString().equals(strategy.trim()))

            throw new InvalidPasswordOptionsException("Unknown strategy.");
    }


    private boolean isPinStrategy(PasswordOptions passwordOptions) {
        return passwordOptions.getPasswordStrategyType().toString().equals("PIN");
    }

    private String doPostProcessorChain(String password, PasswordOptions passwordOptions) {
        if (!isPinStrategy(passwordOptions))
            return passwordPostProcessorChain.apply(password, passwordOptions);
    return password;
    }
}



