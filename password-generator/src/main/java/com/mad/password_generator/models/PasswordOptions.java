package com.mad.password_generator.models;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;

public final class PasswordOptions {

    // === CHAMPS IMMUTABLES ===

    private final int length;
    private final boolean includeUppercase;
    private final boolean includeLowercase;
    private final boolean includeDigits;
    private final boolean includeSpecialChars;
    private final boolean excludeSimilarChars;
    private final boolean requireEachType;
    private final String allowedChars;
    private final String strategy;
    private final String pattern;
    private final String prefix;
    private final String suffix;

    // === CONSTRUCTEUR PRIVE ===
    private PasswordOptions(Builder builder) {
        this.length = builder.length;
        this.includeUppercase = builder.includeUppercase;
        this.includeLowercase = builder.includeLowercase;
        this.includeDigits = builder.includeDigits;
        this.includeSpecialChars = builder.includeSpecialChars;
        this.excludeSimilarChars = builder.excludeSimilarChars;
        this.requireEachType = builder.requireEachType;
         this.strategy = builder.strategy;
        this.pattern = builder.pattern;
        this.allowedChars = builder.allowedChars;
        this.prefix = builder.prefix;
        this.suffix = builder.suffix;
    }

    // === GETTERS ==

    public int getLength() { return length; }
    public boolean isIncludeUppercase() { return includeUppercase; }
    public boolean isIncludeLowercase() { return includeLowercase; }
    public boolean isIncludeDigits() { return includeDigits; }
    public boolean isIncludeSpecialChars() { return includeSpecialChars; }
    public boolean isExcludeSimilarChars() { return excludeSimilarChars; }
    public boolean isRequireEachType() { return requireEachType; }
    public String getAllowedChars() { return allowedChars; }
    public String getStrategy() { return strategy; }
    public String getPattern() { return pattern; }
    public String getPrefix() { return prefix; }
    public String getSuffix() { return suffix; }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private int length = 12; // Valeur par défaut
        private boolean includeUppercase = true;
        private boolean includeLowercase = true;
        private boolean includeDigits = true;
        private boolean includeSpecialChars = false;
        private boolean excludeSimilarChars = false;
        private boolean requireEachType = false;
        private String allowedChars;
        private String strategy = "RANDOM";
        private String pattern;
        private String prefix;
        private String suffix;

        // === MÉTHODES DE CONFIGURATION ===

        public Builder length(int length) {
            this.length = length;
            return this;
        }
        public Builder includeUppercase(boolean value) {
            this.includeUppercase = value;
            return this;
        }
        public Builder includeLowercase(boolean value) {
            this.includeLowercase = value;
            return this;
        }
        public Builder includeDigits(boolean value) {
            this.includeDigits = value;
            return this;
        }
        public Builder includeSpecialChars(boolean value) {
            this.includeSpecialChars = value;
            return this;
        }
        public Builder excludeSimilarChars(boolean value) {
            this.excludeSimilarChars = value;
            return this;
        }
        public Builder requireEachType(boolean value) {
            this.requireEachType = value;
            return this;
        }
        public Builder strategy(String strategy) {
            this.strategy = strategy;
            return this;
        }
        public Builder allowedChars(String allowedChars) {
            this.allowedChars = allowedChars;
            return this;
        }
        public Builder pattern(String pattern) {
            this.pattern = pattern;
            return this;
        }

        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }
        public Builder suffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        // === MÉTHODE FINALE POUR CONSTRUIRE ===
        public PasswordOptions build() {
            // Validation simple
            if (length < 4 || length > 120) {
                throw new InvalidPasswordOptionsException("La longueur du mot de passe doit être comprise entre 4 et 120 caractères.");
            }
            if(!includeUppercase && !includeLowercase && !includeDigits && !includeSpecialChars) {
                throw new InvalidPasswordOptionsException("Au moins un type de caractère doit être sélectionné.");
            }
            if (requireEachType && length < 4) {
                throw new InvalidPasswordOptionsException("Impossible de satisfaire `requireEachType` avec une longueur inférieure à 4."
                );
            }
            return new PasswordOptions(this);
        }
    }
}
