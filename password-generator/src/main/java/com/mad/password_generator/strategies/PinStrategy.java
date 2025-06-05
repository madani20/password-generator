package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Set;

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
public class PinStrategy implements _PasswordGenerationStrategy{
    private static final Logger logger = LoggerFactory.getLogger(PinStrategy.class);
    private final SecureRandom random = new SecureRandom();

    @Override
    public PasswordStrategyType getStrategyType() {
        return PasswordStrategyType.PIN;
    }


    @Override
    public String generate(PasswordOptions options) {
    logger.info("Init generate from PinStrategy");

    validateOptions(options);

    String generatedPinPassword = generatePinPassword(options);

    logger.info("Password generated with PinStrategy");
    return generatedPinPassword;
    }


    //================================== UTILITAIRES ====================================================================

    private void validateOptions(PasswordOptions options) {
        if(!options.isIncludeDigits())
            throw new InvalidPasswordOptionsException("The includeDigits option must be true");
    }

    private String generatePinPassword (PasswordOptions options) {
        //Nombre de chiffres souhaités
        long length = options.getLength();

        // Calcul de la plage maximale
        long maximum = (long) Math.pow(10, length);

        // Générer un nombre aléatoire dans la plage [0, max]
        String password;
        do {
            password = String.format("%0" + length + "d", generateRandomNumberInRange(random, 0, maximum)); //pour conserver les 0 à gauche
        } while (!isPasswordAcceptable(password));

        return password;
    }


        private long generateRandomNumberInRange(SecureRandom random, long min, long max) {
        // Générer un nombre dans [min, max]
        long range = max - min + 1;
        long fraction = (long) (range * random.nextDouble());
        return min + fraction;
    }

    private boolean isPasswordAcceptable(String password) {
        if(password.chars().distinct().count() == 1)
            return true;

        String asc = "01234567890";
        String desc = "09876543210";
        if (asc.contains(password) || desc.contains(password))
            return true;

        // Alternance binaire
        if (password.length() % 2 == 0) {
            String first = password.substring(0, 2);
            StringBuilder pattern = new StringBuilder();

            pattern.append(first.repeat(password.length() / 2));
            if (password.contentEquals(pattern))
                return true;
        }
        return !weakPasswords.contains(password);
    }

    private static final Set<String> weakPasswords = Set.of(
            "000000", "111111", "222222", "123456", "543210", "121212", "112233", "999999","333333", "444444","555555", "666666", "777777",
            "888888", "190000", "200000"
    );




}
