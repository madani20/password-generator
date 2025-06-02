package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * RANDOM_MIXED – Aléatoire classique
 *
 *     But : Générer un mot de passe purement aléatoire avec les caractères autorisés.
 *
 *     Utilise : toutes les options (includeUppercase, etc.)
 *
 *     Exemple : 3@Pfw$LmN9q#
 */
@Component
public class RandomMixedStrategy implements _PasswordGenerationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(RandomMixedStrategy.class);
    private final Random random = new SecureRandom();

    @Override
    public PasswordStrategyType getStrategyType() {
        return PasswordStrategyType.RANDOM;
    }

    @Override
    public String generate(PasswordOptions options) {
        logger.debug("Init generate() from RANDOM strategy");

        StringBuilder charSet = buildCharacterSet(options);

        if (charSet.isEmpty()) {
            throw new InvalidPasswordOptionsException("Aucun caractère sélectionné.");
        }

        if (options.isExcludeSimilarChars() && charSet.length() < options.getLength()) {
            throw new InvalidPasswordOptionsException(
                    "Nombre de caractères uniques insuffisant pour générer un mot de passe de longueur " + options.getLength()
            );
        }

        String password = options.isExcludeSimilarChars()
                ? generateWithoutSimilarChars(options, charSet)
                : generateWithPotentiallySimilarChars(options, charSet);

        logger.debug("Password generated with RANDOM strategy");
        return password;
    }

    //==================================================================================================================

    private StringBuilder buildCharacterSet(PasswordOptions options) {
        if (options.getAllowedChars() != null) {
            return new StringBuilder(options.getAllowedChars());
        }

        StringBuilder set = new StringBuilder();
        if (options.isIncludeUppercase()) set.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (options.isIncludeLowercase()) set.append("abcdefghijklmnopqrstuvwxyz");
        if (options.isIncludeDigits()) set.append("0123456789");
        if (options.isIncludeSpecialChars()) set.append("!@#$%^&*()-_=+[]{}");
        if (options.isIncludeDash()) set.append('-');

        return set;
    }

    private String generateWithoutSimilarChars(PasswordOptions options, StringBuilder charSet) {
        Set<Character> usedChars = new HashSet<>();
        StringBuilder result = new StringBuilder();

        while (result.length() < options.getLength()) {
            char c = charSet.charAt(random.nextInt(charSet.length()));
            if (!usedChars.contains(c)) {
                result.append(c);
                usedChars.add(c);
            }
        }

        return result.toString();
    }

    private String generateWithPotentiallySimilarChars(PasswordOptions options, StringBuilder charSet) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < options.getLength(); i++) {
            char c = charSet.charAt(random.nextInt(charSet.length()));
            result.append(c);
        }

        return result.toString();
    }
}



