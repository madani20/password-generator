package com.mad.password_generator.services;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.dto.PasswordOptionsResponseDTO;
import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.strategies._PasswordGenerationStrategy;
import com.mad.password_generator.tools.PasswordOptionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;



@Service
public class PasswordOptionsService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordOptionsService.class);

    private final PasswordOptionsMapper mapper;
    private final PasswordPostProcessorChain postProcessorChain;
    private final StrategyRegistry strategyRegistry;

    public PasswordOptionsService(PasswordOptionsMapper mapper, PasswordPostProcessorChain postProcessorChain, StrategyRegistry strategyRegistry) {
        this.mapper = mapper;
        this.postProcessorChain = postProcessorChain;
        this.strategyRegistry = strategyRegistry;
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
        logger.info(" Objet options mappé: {}", options.toString());

        /* Sélection de la stratégie */
        _PasswordGenerationStrategy strategy = strategyRegistry.getStrategy(options.getStrategy());

        /* Génération du mot de pasee en fonction de la stratégie */
        String password = strategy.generate(options);

        /* Post-traitements le cas échéant */
        password = postProcessorChain.apply(password, options);

        PasswordOptionsResponseDTO generatedPassword = mapper.toResponseDTO(password);

        logger.info("Mot de passe généré à retourner: {}. Stratégie utilisée: {}.", generatedPassword, strategy);
        logger.info("Fin generate()");

        return generatedPassword;
    }




    //=============================== METHODES UTILITAIRES =========================================================

    private void validateInput(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
    logger.info("Init validateInput");

        if (passwordOptionsRequestDTO.getLength() < 4 || passwordOptionsRequestDTO.getLength() > 120) {
            throw new InvalidPasswordOptionsException("La longueur du mot de passe doit être comprise entre 4 et 120 caractères.");
        }
        if (!requiredEachType(passwordOptionsRequestDTO)) {
            throw new InvalidPasswordOptionsException("Au moins un type de caractère doit être sélectionné.");
        }
        if (requiredEachType(passwordOptionsRequestDTO) && passwordOptionsRequestDTO.getLength() < 4) {
            throw new InvalidPasswordOptionsException("Impossible de satisfaire l'option `requireEachType` avec une longueur inférieure à 4 caractères."
            );
        }
        logger.info("Fin validateInput");
    }

    private boolean requiredEachType(PasswordOptionsRequestDTO passwordOptionsRequestDTO) {
        return passwordOptionsRequestDTO.isIncludeUppercase() && passwordOptionsRequestDTO.isIncludeLowercase() && passwordOptionsRequestDTO.isIncludeDigits()
                && passwordOptionsRequestDTO.isIncludeSpecialChars();
    }
}
