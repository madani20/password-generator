package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;

public class Pronounceable implements _PasswordGenerationStrategy {
    @Override
    public String generate(PasswordOptions options) {
        return "";
    }
    /**
     * PRONOUNCEABLE – Pseudo-mots phonétiques
     *
     *     But : Générer des chaînes de caractères proches de mots prononçables (consonnes/voyelles)
     *
     *     Principe : Alternance CVCVC ou CCVCV (consonne-voyelle)
     *
     *     Exemple : Sutopez, Ladrovin9
     *
     *     Plus facile à mémoriser que RANDOM_MIXED
     */
}
