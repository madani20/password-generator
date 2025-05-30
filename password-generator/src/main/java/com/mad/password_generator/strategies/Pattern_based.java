package com.mad.password_generator.strategies;

import com.mad.password_generator.dto.PasswordOptionsRequestDTO;
import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
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
 *     Exemples de motifs : LL-DD-### -> Ab-CD-843
 *                          #DL-###-DD-LD -> 3Gh-337-PM-jV
 *
 *     Nécessite : un champ pattern dans le payload
 */
@Component
public class Pattern_based implements _PasswordGenerationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(Pattern_based.class);
    private static final Random random = new SecureRandom();

    @Override
    public PasswordStrategyType getStrategyType() {
        return PasswordStrategyType.PATTERN;
    }

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
        if(passwordOptions == null)
        throw new InvalidPasswordOptionsException("Aucune options!");

        String pattern = passwordOptions.getPattern();

        if(!pattern.contains("#") && !pattern.contains("L") && !pattern.contains("D") && !pattern.contains("-")){
            throw new InvalidPasswordOptionsException("Motif incorrect\n");
        }
    }

    private String generatePasswordPattern(PasswordOptions options) {
        logger.info("Init generatedPasswordPattern");

        StringBuilder upperSet = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuilder lowerSet = new StringBuilder("abcdefghijklmnopqrstuvwxyz");
        StringBuilder digitSet = new StringBuilder("0123456789");

        String chosenPattern = options.getPattern();
        StringBuilder passwordToGenerate = new StringBuilder(chosenPattern.length());

        for(int i=0;i<chosenPattern.length();i++) {
            if(chosenPattern.charAt(i) == '-')
                passwordToGenerate.append('-');

            else if (chosenPattern.charAt(i) == 'L') {
                int j = random.nextInt(lowerSet.length());
                passwordToGenerate.append(lowerSet.charAt(j));

            } else if (chosenPattern.charAt(i) == '#') {
                int k = random.nextInt(digitSet.length());
                passwordToGenerate.append(digitSet.charAt(k));

            } else if (chosenPattern.charAt(i) == 'D') {
                int l = random.nextInt(upperSet.length());
                passwordToGenerate.append(upperSet.charAt(l));
            }
            else {
                throw new InvalidPasswordOptionsException("Pattern invalide. Les caractères valides sont: '-', 'L', '#', 'D'\n");
            }
        }
        logger.info("Fin generatedPasswordPattern");
    return passwordToGenerate.toString();
    }

    // if(!hasStrategyAndPattern(passwordOptions))
    //     throw new InvalidPasswordOptionsException("Options requises manquantes.");

    //  if(passwordOptions.getPattern().length() < 6)
    //      throw new InvalidPasswordOptionsException("L'option nécessite un minimum de 6 caractères\n");

    //  private boolean hasStrategyAndPattern(PasswordOptions passwordOptions) {
   //     return passwordOptions.getPasswordStrategyType() == PasswordStrategyType.PATTERN && !passwordOptions.getPattern().isBlank();
   // }

    //private char randomChar(String chars) {
    //    return chars.charAt(random.nextInt(chars.length()));
    //}

}
