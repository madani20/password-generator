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
        String postProcessedPassword = passwordPostProcessorChain.apply(password, options);

        PasswordOptionsResponseDTO generatedPassword = mapper.toResponseDTO(postProcessedPassword);

        logger.info("Fin generate()");

        return generatedPassword;
    }




    //=============================== METHODES UTILITAIRES =========================================================

    private void validateInput(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
    logger.info("Init validateInput");

        if (passwordOptionsRequestDTO.getLength() < 6 || passwordOptionsRequestDTO.getLength() > 128) {
            throw new InvalidPasswordOptionsException("La longueur du mot de passe doit être comprise entre 4 et 120 caractères.");
        }
        if (!passwordOptionsRequestDTO.isIncludeUppercase() && !passwordOptionsRequestDTO.isIncludeLowercase() &&
                !passwordOptionsRequestDTO.isIncludeDigits() && !passwordOptionsRequestDTO.isIncludeSpecialChars()) {
            throw new InvalidPasswordOptionsException("Au moins un type de caractère doit être sélectionné");
        }

        if (requiredEachType(passwordOptionsRequestDTO) && passwordOptionsRequestDTO.getLength() < 6) {
            throw new InvalidPasswordOptionsException("Impossible de satisfaire l'option `requireEachType` avec une longueur inférieure à 6 caractères."
            );
        }
        if (passwordOptionsRequestDTO.getStrategy() == null) {
            throw new InvalidPasswordOptionsException("La stratégie de génération doit exister.");
        }

        logger.info("Fin validateInput");
    }

    private boolean requiredEachType(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        return passwordOptionsRequestDTO.isIncludeUppercase() && passwordOptionsRequestDTO.isIncludeLowercase() && passwordOptionsRequestDTO.isIncludeDigits()
                && passwordOptionsRequestDTO.isIncludeSpecialChars();
    }
   /* private void notNullValidation(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        if(passwordOptionsRequestDTO == null)
            throw new InvalidPasswordOptionsException("Pas d'objet pour générer le mot de passe.");
    }*/
}
