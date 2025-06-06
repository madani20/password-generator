package com.mad.password_generator.models;

import com.mad.password_generator.exceptions.InvalidPasswordOptionsException;

public final class PasswordOptions {

    // === CHAMPS IMMUTABLES ===

    private final int length;
    private final boolean includeUppercase;
    private final boolean includeLowercase;
    private final boolean includeDigits;
    private final boolean includeSpecialChars;
    private final boolean includeDash;
    private final boolean excludeSimilarChars;
    private final boolean excludeAmbiguousChars;
    private final boolean requireEachType;
    private final String allowedChars;
    private final PasswordStrategyType passwordStrategyType;
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
        this.includeDash = builder.includeDash;
        this.excludeSimilarChars = builder.excludeSimilarChars;
        this.passwordStrategyType = builder.strategy;
        this.pattern = builder.pattern;
        this.allowedChars = builder.allowedChars;
        this.excludeAmbiguousChars = builder.excludeAmbiguousChars;
        this.prefix = builder.prefix;
        this.suffix = builder.suffix;

        this.requireEachType = builder.requireEachType;

    }

    // === GETTERS ==

    public int getLength() { return length; }
    public boolean isIncludeUppercase() { return includeUppercase; }
    public boolean isIncludeLowercase() { return includeLowercase; }
    public boolean isIncludeDigits() { return includeDigits; }
    public boolean isIncludeSpecialChars() { return includeSpecialChars; }
    public boolean isIncludeDash() { return includeDash; }
    public PasswordStrategyType getPasswordStrategyType() { return passwordStrategyType;}
    public String getPattern() { return pattern; }
    public String getPrefix() { return prefix; }
    public String getSuffix() { return suffix; }
    public boolean isExcludeSimilarChars() { return excludeSimilarChars; }
    public String getAllowedChars() { return allowedChars; }
    public boolean isExcludeAmbiguousChars() { return excludeAmbiguousChars;
    }

    public boolean isRequireEachType() { return requireEachType; }


    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private int length = 12; // Valeur par défaut
        private boolean includeUppercase = false;
        private boolean includeLowercase = false;
        private boolean includeDigits = false;
        private boolean includeSpecialChars = false;
        private boolean includeDash = false;
        private PasswordStrategyType strategy = PasswordStrategyType.RANDOM;
        private String pattern;
        private String prefix;
        private String suffix;
        private boolean excludeSimilarChars = false;
        private boolean excludeAmbiguousChars;
        private String allowedChars;

        private boolean requireEachType = false;



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
        public Builder includeDash(boolean value) {
            this.includeDash = value;
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
        public Builder strategy(PasswordStrategyType strategy) {
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
        public Builder excludeAmbiguousChars(boolean value) {
            this.excludeAmbiguousChars = value;
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
            if (length < 6 || length > 128) {
                throw new InvalidPasswordOptionsException("La longueur du mot de passe doit être comprise entre 6 et 128 caractères.");
            }

           return new PasswordOptions(this);
        }
    }
}
