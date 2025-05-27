package com.mad.password_generator.strategies;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("PIN")
public class Pin {
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
