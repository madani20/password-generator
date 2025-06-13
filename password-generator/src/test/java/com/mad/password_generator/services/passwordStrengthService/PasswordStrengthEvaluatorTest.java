package com.mad.password_generator.services.passwordStrengthService;

import com.mad.password_generator.dto.PasswordStrengthRequestDTO;
import com.mad.password_generator.dto.PasswordStrengthResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PasswordStrengthEvaluatorTest {

    private PasswordStrengthEvaluator passwordStrengthEvaluator;

    @BeforeEach
    void setUp() {
        passwordStrengthEvaluator = new PasswordStrengthEvaluator();
    }


    @Test
    @DisplayName("Should return 'Très faible' for simple commun password.")
    void testAnalyze_shouldReturnTresFaibleForSimpleCommonPassword() {
        PasswordStrengthRequestDTO request = new PasswordStrengthRequestDTO("password");
        PasswordStrengthResponseDTO result = passwordStrengthEvaluator.analyze(request);

        assertThat(result.getScore()).isLessThanOrEqualTo((short)2);
        assertThat(result.getLevel()).isEqualTo("Très faible");
        assertThat(result.getSuggestions()).contains("Ajoutez des chiffres","Ajoutez des caractères spéciaux");
    }
    @Test
    @DisplayName("Should detect repeated characters")
    void testAnalyze_shouldDetectRepeatedCharacters() {
        PasswordStrengthRequestDTO request = new PasswordStrengthRequestDTO("aaaBBB111!!!");
        PasswordStrengthResponseDTO result = passwordStrengthEvaluator.analyze(request);

        assertThat(result.getScore()).isLessThan((short) 10);
        assertThat(result.getSuggestions()).contains("Évitez les répétitions de lettres/chiffres");
    }

    @Test
    @DisplayName("Should detect sequential characters")
    void testAnalyze_shouldDetectSequentialCharacters() {
        PasswordStrengthRequestDTO request = new PasswordStrengthRequestDTO("abc123ABC!");
        PasswordStrengthResponseDTO result = passwordStrengthEvaluator.analyze(request);

        assertThat(result.getSuggestions()).contains("Évitez les séquences (abc, 123…)");
    }
    @Test
    @DisplayName("Should detect dictionnary word")
    void testAnalyze_shouldDetectDictionaryWord() {
        PasswordStrengthRequestDTO request = new PasswordStrengthRequestDTO("admin");
        PasswordStrengthResponseDTO result = passwordStrengthEvaluator.analyze(request);

        assertThat(result.getSuggestions()).contains("Évitez les mots de passe trop courants");
    }
    @Test
    @DisplayName("Should return 'Très bonne' for strong password")
    void testAnalyze_shouldReturnTresBonneForStrongPassword() {
        PasswordStrengthRequestDTO request = new PasswordStrengthRequestDTO("T9@mD2!vB7$c");
        PasswordStrengthResponseDTO result = passwordStrengthEvaluator.analyze(request);

        assertThat(result.getScore()).isGreaterThanOrEqualTo((short)9);
        assertThat(result.getLevel()).isEqualTo("Très bonne");
        assertThat(result.getSuggestions()).isEmpty();
    }
    @Test
    @DisplayName("Should throw exception for empty password!")
    void testAnalyze_shouldThrowExceptionForEmptyPassword() {
        PasswordStrengthRequestDTO request = new PasswordStrengthRequestDTO("   ");
        assertThatThrownBy(() -> passwordStrengthEvaluator.analyze(request))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Password must exist");
    }
}