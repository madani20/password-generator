package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PinStrategyTest {

    private final PinStrategy pinStrategy = new PinStrategy();

    @Test
    @DisplayName("Should generate PIN with correct length and digits only")
    void testGenerate_ShouldGenerateCorrectPinPasswordWithOnlyDigits() {
        PasswordOptions options = PasswordOptions.builder()
                .length(6)
                .strategy(PasswordStrategyType.PIN)
                .includeDigits(true)
                .build();

        String result = pinStrategy.generate(options);

        assertNotNull(result);
        assertEquals(6, result.length());
        assertTrue(result.matches("\\d{6}"));
    }
    @Test
    @DisplayName("Should throw exception when includeDigits is false")
    void testGenerate_ShouldThrowWhenIncludeDigitsIsFalse() {
        PasswordOptions options = PasswordOptions.builder()
                .length(6)
                .strategy(PasswordStrategyType.PIN)
                .includeDigits(false)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> {
            pinStrategy.generate(options);
        });
    }
    @Test
    @DisplayName("Should not generate blacklisted or weak PINs")
    void testGenerate_ShouldAvoidWeakPatterns() {
        PasswordOptions options = PasswordOptions.builder()
                .length(6)
                .strategy(PasswordStrategyType.PIN)
                .includeDigits(true)
                .build();

        for (int i = 0; i < 2000; i++) {
            String pin = pinStrategy.generate(options);
            assertNotEquals("000000", pin);
            assertNotEquals("111111", pin);
            assertNotEquals("222222", pin);
            assertNotEquals("444444", pin);
            assertNotEquals("555555", pin);
            assertNotEquals("123456", pin);
            assertFalse(pin.matches("(\\d)\\1{3}")); // 4 chiffres identiques consécutifs



        }
    }




}