package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

public class Random_mixedTest {

    private final Random_mixed strategy = new Random_mixed();

    @Test
    @DisplayName("Should generate password with length = 8 (default valid case)")
    void testGenerate_shouldGeneratePasswordWithLength8() {
        PasswordOptions options = PasswordOptions.builder()
                .length(8)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);

        assertEquals(8, password.length());
        assertTrue(password.matches("^[a-zA-Z0-9]+$"));
    }

    @Test
    @DisplayName("Should throw exception for length < 6")
    void testGenerate_shouldThrowExceptionForLengthTooShort() {
        PasswordOptions options = PasswordOptions.builder()
                .length(5)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    @DisplayName("Should throw exception for length > 128")
    void testGenerate_shouldThrowExceptionForLengthTooLong() {
        PasswordOptions options = PasswordOptions.builder()
                .length(129)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    @DisplayName("Should generate only lowercase characters")
    void testGenerate_shouldGenerateLowercaseOnly() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .includeUppercase(false)
                .includeDigits(false)
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
                .includeDigits(true)
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
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        Set<String> results = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            results.add(strategy.generate(options));
        }

        assertTrue(results.size() > 90); // Teste l'aléa suffisant
    }

    @Test
    @DisplayName("Should generate valid password at max boundary (length 128)")
    void testGenerate_shouldGenerateMaxLengthPassword() {
        PasswordOptions options = PasswordOptions.builder()
                .length(128)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertEquals(128, password.length());
    }

    @Test
    @DisplayName("Should generate valid password at min boundary (length 6)")
    void testGenerate_shouldGenerateMinLengthPassword() {
        PasswordOptions options = PasswordOptions.builder()
                .length(6)
                .strategy(PasswordStrategyType.RANDOM)
                .build();

        String password = strategy.generate(options);
        assertEquals(6, password.length());
    }
}
