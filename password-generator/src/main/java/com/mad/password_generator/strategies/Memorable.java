package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;

public class Memorable implements _PasswordGenerationStrategy {
    @Override
    public String generate(PasswordOptions options) {
        return "";
    }

    @Override
    public PasswordStrategyType getStrategyType() {
        return null;
    }
}
/**
 MEMORABLE – Lisible par un humain

 But : Générer un mot de passe plus facilement mémorisable, sans trop sacrifier la sécurité.

 Principe : Génère des blocs de type "mot-séparateur-chiffre"

 Exemple : Tulipe-83-Flocon
 */