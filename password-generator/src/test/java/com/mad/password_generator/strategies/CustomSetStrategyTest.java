package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomSetStrategyTest {

    private CustomSetStrategy customSetStrategy;

    @BeforeEach
    public void setUp(){
        customSetStrategy =  new CustomSetStrategy();
    }

    @Test
    @DisplayName("Should generate password with characters from allowed set only")
    void testGenerate_shouldGeneratePasswordWithValidAllowedChars() {
        String allowedChars = "5Ko!D";
        int length = 20;

        PasswordOptions options = PasswordOptions.builder()
                .length(length)
                .strategy(PasswordStrategyType.CUSTOM_SET)
                .allowedChars(allowedChars)
                .build();

        String password = customSetStrategy.generate(options);

        assertNotNull(password, "Password should not be null");
        assertEquals(length, password.length(), "Password should have the correct length");
        assertTrue(password.matches("^[5Ko!D]+$"), "Password should only contain characters from the allowed set");
    }

    @Test
    @DisplayName("Should throw InvalidPasswordOptionsException when allowedChars is null or empty")
    void testGenerate_shouldThrowWhenAllowedCharsIsEmpty() {
        PasswordOptions options = PasswordOptions.builder()
                .length(10)
                .strategy(PasswordStrategyType.CUSTOM_SET)
                .allowedChars("")
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> customSetStrategy.generate(options));
    }

}