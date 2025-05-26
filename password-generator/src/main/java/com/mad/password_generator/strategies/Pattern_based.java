package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;

public class Pattern_based implements _PasswordGenerationStrategy {
    @Override
    public String generate(PasswordOptions options) {
        return "";
    }
    /**
     * PATTERN_BASED – Basé sur un motif utilisateur
     *
     *     But : L’utilisateur définit un motif (ex : LLDDLL) pour Lettre, Lettre, Chiffre, etc.
     *
     *     Exemple de motif : LL-DD-### ⟶ Ab-CD-843
     *
     *     Nécessite : un champ pattern dans le payload
     */
}
