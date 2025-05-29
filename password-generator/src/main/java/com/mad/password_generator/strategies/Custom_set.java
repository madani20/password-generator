package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;

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
public class Custom_set  implements _PasswordGenerationStrategy {
    @Override
    public String generate(PasswordOptions options) {
        return "";
    }

    @Override
    public PasswordStrategyType getStrategyType() {
        return null;
    }

}
