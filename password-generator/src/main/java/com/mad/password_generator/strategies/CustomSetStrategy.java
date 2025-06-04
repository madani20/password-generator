package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * CUSTOM_SET – Utilisation d’un alphabet défini
 *
 *     But : L’utilisateur passe explicitement un ensemble de caractères possibles
 *
 *     Exemple :
 *
 * {
 *   "allowedChars": "ABC123*!",
 *   "length": 10
 * }
 */
@Component
public class CustomSetStrategy implements _PasswordGenerationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(CustomSetStrategy.class);
    private static final Random random = new SecureRandom();

    @Override
    public PasswordStrategyType getStrategyType() {
        return PasswordStrategyType.CUSTOM_SET;
    }

    @Override
    public String generate(PasswordOptions options) {
        logger.debug("Init generate from CustomSetStrategy");

        validateOptions(options);

        String generatedCustomSetPassword = generateCustomSetPassword(options);

        logger.debug("Password generated with CustomSetStrategy");
        return generatedCustomSetPassword;
    }

    //================================ UTILITAIRES ==================================================================

    private void validateOptions(PasswordOptions passwordOptions) {
        if(passwordOptions.getAllowedChars() == null || passwordOptions.getAllowedChars().isBlank())
            throw new InvalidPasswordOptionsException("You must provide a non-empty character set.");

        if(passwordOptions.getAllowedChars().length() < 3)
            throw new InvalidPasswordOptionsException("The set of characters must contain at least 3 characters.");

        if(passwordOptions.getLength() < 6)
            throw new InvalidPasswordOptionsException("Password length must be between 6 and 128.");
    }


    private String generateCustomSetPassword(PasswordOptions passwordOptions) {
        logger.debug("Init generateCustomSetPassword");

        String allowedChars = passwordOptions.getAllowedChars();
        int length = passwordOptions.getLength();

        StringBuilder result = new StringBuilder();

        for(int i=0;i<length;i++) {
            result.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        logger.debug("Fin generateCustomSetPassword");
        return result.toString();
    }
}
