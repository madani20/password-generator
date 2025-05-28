package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class pattern_BasedTest {

    private Pattern_based strategy;

    @BeforeEach
    void setup() {
        strategy = new Pattern_based();
    }

    @Test
    void shouldGeneratePasswordWithValidPattern() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#D")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = strategy.generate(options);

        assertNotNull(password);
        assertEquals(3, password.length());
        assertTrue(Character.isLowerCase(password.charAt(0)));
        assertTrue(Character.isDigit(password.charAt(1)));
        assertTrue(Character.isUpperCase(password.charAt(2)));
    }

    @Test
    void shouldThrowExceptionWhenPatternIsNull() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern(null)
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    void shouldThrowExceptionWhenPatternIsEmpty() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    void shouldThrowExceptionWhenPatternHasNoRecognizedSymbol() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("XXX")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    void shouldThrowExceptionWhenPatternContainsInvalidCharacter() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#D*") // '*' is not handled
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> strategy.generate(options));
    }

    @Test
    void shouldGeneratePasswordWithDashesPreserved() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L-#-D")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = strategy.generate(options);

        assertNotNull(password);
        assertEquals(5, password.length());
        assertEquals('-', password.charAt(1));
        assertEquals('-', password.charAt(3));
    }
}
