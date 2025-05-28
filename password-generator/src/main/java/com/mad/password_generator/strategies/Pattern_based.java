package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;


/**
 * PATTERN_BASED – Basé sur un motif utilisateur
 *
 *     But : L’utilisateur définit un motif (ex : LLDDLL) pour Lettre, Lettre, Chiffre, etc.
 *
 *     Exemple de motif : LL-DD-### ⟶ Ab-CD-843
 *
 *     Nécessite : un champ pattern dans le payload
 */
@Component("PATTERN")
public class Pattern_based implements _PasswordGenerationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(Pattern_based.class);

    @Override
    public String generate(PasswordOptions options) {
    logger.info("Init generate from Pattern_based");

        validateOptions(options);

        String generatedPasswordPattern = generatePasswordPattern(options);

    logger.info("Fin generate from Pattern_based");
        return generatedPasswordPattern;
    }


    //==============================  UTILITAIRES  ===========================================================

    private void validateOptions(PasswordOptions passwordOptions) {
        if (passwordOptions.getPattern() == null || passwordOptions.getPattern().isEmpty()) {
            throw new InvalidPasswordOptionsException("Un motif est requis pour la stratégie PATTERN");
        }
    }
    private String generatePasswordPattern(PasswordOptions options) {
        logger.info("Init generatedPasswordPattern");
        Random random = new SecureRandom();

        StringBuilder upperSet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder lowerSet = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        //StringBuilder specialSet = new StringBuilder("!@#$%^&*()-_=+[]{}");
        StringBuilder digitSet = new StringBuilder("0123456789");

        String chosenPattern = options.getPattern();
        StringBuilder generatedPassword = new StringBuilder(chosenPattern.length());

        for(int i=0;i<chosenPattern.length();i++) {
            if(chosenPattern.charAt(i) == '-')
                generatedPassword.append('-');

            else if (chosenPattern.charAt(i) == 'L') {
                int j = random.nextInt(lowerSet.length());
                generatedPassword.append(lowerSet.charAt(j));

            } else if (chosenPattern.charAt(i) == '#') {
                int k = random.nextInt(digitSet.length());
                generatedPassword.append(digitSet.append(k));

            } else if (chosenPattern.charAt(i) == 'D') {
                int l = random.nextInt(upperSet.length());
                generatedPassword.append(upperSet.append(l));
            }
        }
        logger.info("Fin generatedPasswordPattern");
    return generatedPassword.toString();
    }

}
