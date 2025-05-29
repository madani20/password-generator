package com.mad.password_generator.strategies;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class Pattern_basedTest {

    private Pattern_based patternStrategy;

    @BeforeEach
    void setup() {
        patternStrategy = new Pattern_based();
    }

    @Test
    @DisplayName("Should Generate Password With Valid Pattern")
    void testGenerate_shouldGeneratePasswordWithValidPattern() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#D#DL")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);

        assertNotNull(password);
        assertEquals(6, password.length());
        assertTrue(Character.isLowerCase(password.charAt(0)));
        assertTrue(Character.isDigit(password.charAt(1)));
        assertTrue(Character.isUpperCase(password.charAt(2)));
    }

    @Test
    @DisplayName("Should Throw Exception When Pattern Is Null")
    void testGenerate_shouldThrowExceptionWhenPatternIsNull() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern(null)
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionWhenPatternIsEmpty() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForEmptyPatternWithWhitespace() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("   ") // string non null mais inutile
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    @DisplayName("Should throw exception for pattern containing spaces")
    void testGenerate_shouldThrowExceptionForPatternContainingSpaces() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L # D ")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForPatternWithSpecialSymbols() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#@DLL") // '@' non reconnu
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForPatternWithSingleInvalidSymbol() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("X") // caractère non reconnu
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForLowercasePatternSymbols() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("l#d") // minuscules: non gérés
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForPatternWithEmoji() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("DDL#D😀")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }


    @Test
    @DisplayName("Should ignore other options and only use pattern length = 999")
    void testGenerate_shouldIgnoreOtherOptionsAndOnlyUsePattern1() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#DLL#")
                .length(999) // doit être ignoré ici
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);

        assertEquals(6, password.length());
    }

    @Test
    @DisplayName("Should ignore other options and only use pattern length = 2")
    void testGenerate_shouldIgnoreOtherOptionsAndOnlyUsePattern2() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#D##L")
                .length(3) // doit être ignoré ici
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);

        assertEquals(6, password.length());
    }

    @Test
    void testGenerate_shouldThrowExceptionForPatternWithUnicode() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#Dç-D")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForPatternWithAccentedLetters() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("LéD##L") // 'é' non prévu
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionForPatternWithNewLine() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L\nD###")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldGenerateDigitsOnlyPassword() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("######")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);

        assertTrue(password.matches("\\d{6}"));
    }

    @Test
    void testGenerate_shouldGeneratePasswordWithFixedAndVariableChars() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L-L-D-#")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);
        assertEquals(7, password.length());
        assertEquals('-', password.charAt(1));
        assertEquals('-', password.charAt(3));
        assertEquals('-', password.charAt(5));
    }


    @Test
    void testGenerate_shouldGeneratePasswordWithOnlyDashes() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("------")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);
        assertEquals("------", password);
    }


    @Test
    void testGenerate_shouldThrowExceptionForPatternWithMixedFixedCharacters() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("Lx#yD#") // x et y sont non gérés
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionWhenPatternHasNoRecognizedSymbol() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("XXX")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldThrowExceptionWhenPatternContainsInvalidCharacter() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#D*") // '*' is not handled
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        assertThrows(InvalidPasswordOptionsException.class, () -> patternStrategy.generate(options));
    }

    @Test
    void testGenerate_shouldGeneratePasswordWithDashesPreserved() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L-#-DD")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        String password = patternStrategy.generate(options);

        assertNotNull(password);
        assertEquals(6, password.length());
        assertEquals('-', password.charAt(1));
        assertEquals('-', password.charAt(3));
    }

    @Test
    void testGenerate_shouldGenerateDifferentPasswordsEachTime() {
        PasswordOptions options = PasswordOptions.builder()
                .pattern("L#D#DL")
                .strategy(PasswordStrategyType.PATTERN)
                .build();

        Set<String> passwords = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            passwords.add(patternStrategy.generate(options));
        }

        assertTrue(passwords.size() > 90); // forte unicité attendue
    }

}

