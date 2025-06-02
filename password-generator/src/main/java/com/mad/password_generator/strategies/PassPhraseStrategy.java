package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 *  PASS_PHRASE – Phrase secrète
 *
 *     But : Générer un mot de passe à partir de plusieurs mots aléatoires (type Diceware).
 *
 *     Principe : Sélection de 3 à 5 mots dans un dictionnaire de mots sûrs.
 *
 *     Exemple : chien-velo-journee-livre
 *
 *     Options spéciales : permet d’ajouter chiffre ou ponctuation (avec includeDigits, etc.)
 */
@Component
public class PassPhraseStrategy implements _PasswordGenerationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PassPhraseStrategy.class);

    private final Random random = new SecureRandom();

    @Override
    public PasswordStrategyType getStrategyType() {
        return PasswordStrategyType.PASS_PHRASE;
    }

    @Override
    public String generate(PasswordOptions options) {


        return "";
    }

}
