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

        validateInput(charSet, options);

        if (options.isExcludeAmbiguousChars()) {
            deleteAmbiguousChars(charSet);
        }
        String password;
        boolean excludeDuplicates = options.isExcludeSimilarChars();
        password = generatePassword(options.getLength(), charSet, excludeDuplicates);

        logger.debug("Password generated with RANDOM strategy");
        return password;
    }

    //========================================[ UTILITAIRES ]==========================================================================

    private void validateInput(StringBuilder charSet, PasswordOptions options) {
        if (charSet.isEmpty()) {
            throw new InvalidPasswordOptionsException("No character selected");
        }
        if (options.isExcludeSimilarChars() && charSet.length() < options.getLength()) {
            logger.info("charSet.length(): {} , options.getLength(): {}", charSet.length(), options.getLength());
            throw new InvalidPasswordOptionsException(
                    "Insufficient number of unique characters to generate a password of length " + options.getLength()
            );
        }
    }

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
    private String generatePassword(int length, StringBuilder charSet, boolean excludeDuplicates) {
        Set<Character> usedChars = excludeDuplicates ? new HashSet<>() : null;
        StringBuilder result = new StringBuilder();

        while (result.length() < length) {
            char c = charSet.charAt(random.nextInt(charSet.length()));
            if (excludeDuplicates && usedChars.contains(c))
                continue;

            result.append(c);
            if (excludeDuplicates)
                usedChars.add(c);
        }
        return result.toString();
    }


    private void deleteAmbiguousChars(StringBuilder charSet) {
        for (int i = charSet.length() - 1; i >= 0; i--) {
            if (ambiguousChar.contains(charSet.charAt(i))) {
                charSet.deleteCharAt(i);
            }
        }
    }

    private static final Set<Character> ambiguousChar = Set.of(
            '0','O','1','l','I','|'
    );
}



