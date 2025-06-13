package com.mad.password_generator.services.passwordStrengthService;

import com.mad.password_generator.dto.PasswordStrengthRequestDTO;
import com.mad.password_generator.dto.PasswordStrengthResponseDTO;
import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


/**
 *      Heuristique simple
 *
 *      Donne un score basé sur la composition du mot de passe:
 *
 */
@Component
public class PasswordStrengthEvaluator implements _PasswordStrengthAnalysisService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordStrengthEvaluator.class);

    @Override
    public PasswordStrengthResponseDTO analyze(PasswordStrengthRequestDTO password) {
        logger.info("Init analyze");

        validateInput(password.getPassword());

        short score = getScore(password.getPassword());

        String level = getPasswordLevel(score);

        return new PasswordStrengthResponseDTO(score, level, suggest(password.getPassword()));
    }




    //============================ [ UTILITAIRES ] =============================================================

    private void validateInput(String password) {
        if (password == null || password.isBlank())
            throw new InvalidPasswordOptionsException("Password must exist to be analyzed!");
    }
    private short getScore(String password){
        short score =  getScoreWithSimpleHeuristic(password);
        if (hasRepeatedChars(password))        score--;
        if (hasSequentialChars(password))      score--;
        if (isDictionaryWord(password))        score-=2;

        return (short) Math.min(10, Math.max(0, score));
    }

    private short getScoreWithSimpleHeuristic(String password) {
        short score = 0;
        if (isLongEnough(password))   score += 2;
        if (password.length() >= 16)  score += 1;
        if (isContainCapitalLetter(password))  score += 1;
        if (isContainDigit(password))          score += 1;
        if (isContainLowercase(password))      score += 1;
        if (isContainSomeSpecialChar(password))      score += 2;
        if(isContainSomeSpecialChar(password) && isContainLowercase(password) && isContainCapitalLetter(password) && isContainDigit(password)) score += 1;
        if (!hasRepeatedChars(password))        score += 1;
        if (!hasSequentialChars(password))      score += 1;

        logger.info("Fin getScoreWithSimpleHeuristic, score {}", score);
        return score;
    }

    private boolean hasRepeatedChars(String p) {
        return Pattern.compile("(.)\\1{2,}").matcher(p).find(); // correspond à "aaa", "111", "!!!" n'importe où dans la chaîne
    }

    private boolean hasSequentialChars(String p){
        String seq = "abcdefghijklmnopqrstuvwxyz0123456789";
        String reversedSeq = new StringBuilder(seq).reverse().toString();
        String lower = p.toLowerCase();
        for (int i = 0; i < lower.length() - 2; i++) {
            String sub = lower.substring(i, i + 3);
            if (seq.contains(sub) || reversedSeq.contains(sub)) return true;
        }
        return false;
    }

    private boolean isDictionaryWord(String p){
        String lower = p.toLowerCase();
        return Set.of("password","azerty","qwerty","admin", "1234", "123456", "12345678","1234567890", "marseille", "nicolas", "camille", "doudou",
                "sunshine", "welcome", "letmein", "loulou", "Qwerty123", "chouchou").contains(lower);
    }

    private String getPasswordLevel(short score) {
        return getLevel(score);
    }

    private String getLevel(short score) {
        String level = "";
        switch (score) {
            case 0, 1, 2 -> {  level = "Très faible";
            }
            case 3, 4 -> {     level = "Faible";
            }
            case 5, 6 -> {     level = "Moyenne";
            }
            case 7, 8 -> {     level = "Bonne";
            }
            case 9, 10 -> {   level = "Très bonne";
            }
            default -> {
                return "Inconnu";
            }
        }
        return level;
    }
    
    private List<String> suggest(String password){
        List<String> suggestions = new ArrayList<>();
        if(!isLongEnough(password))
            suggestions.add("Utilisez au moins 12 caractères");
        if(!isContainDigit(password))
            suggestions.add("Ajoutez des chiffres");
        if (!isContainLowercase(password))
            suggestions.add("Ajoutez des lettres en minuscule");
        if (!isContainCapitalLetter(password))
            suggestions.add("Ajoutez des lettres en majuscules");
        if (!isContainSomeSpecialChar(password))
            suggestions.add("Ajoutez des caractères spéciaux");
        if(hasRepeatedChars(password))
            suggestions.add("Évitez les répétitions de lettres/chiffres");
        if(hasSequentialChars(password))
            suggestions.add("Évitez les séquences (abc, 123…)");
        if(isDictionaryWord(password))
            suggestions.add("Évitez les mots de passe trop courants");

        return suggestions;
    }

    private boolean isLongEnough(String password) {
        return password.length() >= 12;
    }

    private boolean isContainCapitalLetter(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean isContainLowercase(String password) {
        return password.matches(".*[a-z].*");
    }

    private boolean isContainDigit(String password) {
        return password.matches(".*\\d.*");
    }

    private boolean isContainSomeSpecialChar(String password) {
        return password.matches(".*[^a-zA-Z0-9].*"); //"!@#$%^&*()-_=+[]{}:;~,?");
    }
}





/**
 *

 private String levelEntropy(double score) {
 if (score < 30) return "Faible";
 else if (score < 60) return "Moyenne";
 else if (score < 80) return "Forte";
 else
 return "Très forte";
 }
 private double calculateEntropy(String password) {
 double pool = getCharacterPoolSize(password);
 return password.length() * Math.log(pool) / Math.log(2);
 }

 private double getCharacterPoolSize(String password) {
 double pool = 0;
 if (isContainLowercase(password)) pool += 26;
 if (isContainCapitalLetter(password)) pool += 26;
 if (isContainDigit(password)) pool += 10;
 if (isContainSomeSpecialChar(password)) pool += 32;
 return pool;
 }

 */

