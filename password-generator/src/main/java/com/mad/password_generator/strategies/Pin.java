package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * PIN – Code numérique
 *
 *     But : Générer un code PIN numérique, généralement court.
 *
 *     Ignore : tous les paramètres sauf length et includeDigits
 *
 *     Exemple : 8291
 */
@Component
public class Pin implements _PasswordGenerationStrategy{

    private final Random random = new SecureRandom();

    @Override
    public String generate(PasswordOptions options) {

        return "";
    }

    @Override
    public PasswordStrategyType getStrategyType() {
        return PasswordStrategyType.PIN;
    }


}
