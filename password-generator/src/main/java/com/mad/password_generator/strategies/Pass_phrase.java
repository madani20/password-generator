package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;

public class Pass_phrase implements _PasswordGenerationStrategy {
    @Override
    public String generate(PasswordOptions options) {
        return "";
    }
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
}
