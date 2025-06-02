package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RandomMixedStrategyTest {

    private final RandomMixedStrategy strategy = new RandomMixedStrategy();

    @Test
    @DisplayName("Should generate password with length = 8 (default valid case)")
    void testGenerate_shouldGeneratePasswordWithLength8() {
        PasswordOptions options = PasswordOptions.builder()
                .length(8)
                .includeLowercase(true)
                .includeUppercase(false)
                .includeDigits(false)
                .includeDash(false)
                .includeSpecialChars(false)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);

        assertEquals(8, password.length());
        assertTrue(password.matches("^[a-z]+$")); // uniquement lowercase ici
    }

    @Test
    @DisplayName("Should throw exception for length < 6")
    void testGenerate_shouldThrowExceptionForLengthTooShort() {
        assertThrows(InvalidPasswordOptionsException.class, () ->
                PasswordOptions.builder()
                        .length(5)
                        .includeLowercase(true)
                        .strategy(PasswordStrategyType.RANDOM)
                        .build()
        );
    }

    @Test
    @DisplayName("Should throw exception for length > 128")
    void testGenerate_shouldThrowExceptionForLengthTooLong() {
        assertThrows(InvalidPasswordOptionsException.class, () ->
                PasswordOptions.builder()
                        .length(129)
                        .includeLowercase(true)
                        .strategy(PasswordStrategyType.RANDOM)
                        .build()
        );
    }

    @Test
    @DisplayName("Should generate only lowercase characters")
    void testGenerate_shouldGenerateLowercaseOnly() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .includeUppercase(false)
                .includeDigits(false)
                .includeSpecialChars(false)
                .includeLowercase(true)
                .includeDash(false)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertTrue(password.matches("^[a-z]{10}$"));
    }

    @Test
    @DisplayName("Should generate only uppercase characters")
    void testGenerate_shouldGenerateUppercaseOnly() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .includeLowercase(false)
                .includeDigits(false)
                .includeSpecialChars(false)
                .includeUppercase(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertTrue(password.matches("^[A-Z]{10}$"));
    }

    @Test
    @DisplayName("Should generate only digits")
    void testGenerate_shouldGenerateDigitsOnly() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .includeLowercase(false)
                .includeUppercase(false)
                .includeSpecialChars(false)
                .includeDigits(true)
                .includeDash(false)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertTrue(password.matches("^\\d{10}$"));
    }

    @Test
    @DisplayName("Should throw exception if all char types are excluded")
    void testGenerate_shouldThrowExceptionWhenNoCharTypeIncluded() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .includeLowercase(false)
                .includeUppercase(false)
                .includeDigits(false)
                .includeSpecialChars(false)
                .includeDash(false)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }


    @Test
    @DisplayName("Should generate different passwords each time")
    void testGenerate_shouldGenerateDifferentPasswordsEachTime() {
        PasswordOptions options = PasswordOptions.builder()
                .length(12)
                .includeLowercase(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        Set<String> results = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            results.add(strategy.generate(options));
        }

        assertTrue(results.size() > 90, "Generated passwords are not random enough");
    }

    @Test
    @DisplayName("Should generate valid password at max boundary (length 128)")
    void testGenerate_shouldGenerateMaxLengthPassword() {
        PasswordOptions options = PasswordOptions.builder()
                .length(128)
                .includeLowercase(true)
                .excludeSimilarChars(false) // On autorise ici les doublons, sinon on n'arrive pas à 128
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertEquals(128, password.length());
    }
    @Test
    @DisplayName("Should generate unique lowercase chars only when excludeSimilarChars = true")
    void testGenerate_shouldGenerateUniqueLowercaseChars() {
        PasswordOptions options = PasswordOptions.builder()
                .length(26)
                .includeLowercase(true)
                .excludeSimilarChars(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertEquals(26, password.length());

        // Pour s'assurer qu'il n'y a pas de doublon
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : password.toCharArray()) {
            assertTrue(uniqueChars.add(c), "Character repeated: " + c);
        }
    }

    @Test
    @DisplayName("Should generate valid password at min boundary (length 6)")
    void testGenerate_shouldGenerateMinLengthPassword() {
        PasswordOptions options = PasswordOptions.builder()
                .length(6)
                .includeLowercase(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertEquals(6, password.length());
    }

    @Test
    @DisplayName("Should generate password using only allowedChars")
    void testGenerate_shouldUseOnlyAllowedChars() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .allowedChars("XYZ")
                .excludeSimilarChars(false)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertTrue(password.matches("^[XYZ]{10}$"));
    }

    @Test
    @DisplayName("Should generate password without duplicate characters when excludeSimilarChars is true")
    void testGenerate_shouldExcludeRepeatedCharacters() {
        PasswordOptions options = PasswordOptions.builder()
                .length(8)
                .includeLowercase(true)
                .excludeSimilarChars(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        Set<Character> seen = new HashSet<>();
        for (char c : password.toCharArray()) {
            assertTrue(seen.add(c), "Character '" + c + "' is repeated");
        }
    }

    @Test
    @DisplayName("Should throw if not enough unique characters for excludeSimilarChars")
    void testGenerate_shouldThrowWhenNotEnoughUniqueChars() {
        PasswordOptions options = PasswordOptions.builder()
                .length(6)
                .allowedChars("abc")
                .excludeSimilarChars(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    @DisplayName("Should generate password including all types of characters")
    void testGenerate_shouldGeneratePasswordWithAllOptions() {
        PasswordOptions options = PasswordOptions.builder()
                .length(20)
                .includeUppercase(true)
                .includeLowercase(true)
                .includeDigits(true)
                .includeSpecialChars(true)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertEquals(20, password.length());
    }
}
