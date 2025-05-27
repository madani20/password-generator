package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

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
@Qualifier("RANDOM")
public class Random_mixed implements _PasswordGenerationStrategy {

    private final Random random = new SecureRandom();

    @Override
    public String generate(PasswordOptions options) {
        StringBuilder charset = new StringBuilder();

        if (options.isIncludeUppercase()) charset.append("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        if (options.isIncludeLowercase()) charset.append("abcdefghijklmnopqrstuvwxyz");
        if (options.isIncludeDigits()) charset.append("0123456789");
        if (options.isIncludeSpecialChars()) charset.append("!@#$%^&*()-_=+[]{}");

        if (options.getAllowedChars() != null) {
            charset = new StringBuilder(options.getAllowedChars());
        }

        if (charset.isEmpty()) {
            throw new IllegalArgumentException("Aucun caractère sélectionné.");
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < options.getLength(); i++) {
            int index = random.nextInt(charset.length());
            result.append(charset.charAt(index));
        }
        return result.toString();
    }
}


