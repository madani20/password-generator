package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component("PIN")
public class Pin implements _PasswordGenerationStrategy{

    private final Random random = new SecureRandom();

    @Override
    public String generate(PasswordOptions options) {

        return "";
    }
    /**
     * PIN – Code numérique
     *
     *     But : Générer un code PIN numérique, généralement court.
     *
     *     Ignore : tous les paramètres sauf length et includeDigits
     *
     *     Exemple : 8291
     */
}
