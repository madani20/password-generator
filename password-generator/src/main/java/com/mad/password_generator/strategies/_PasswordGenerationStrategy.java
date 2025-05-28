package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.models.PasswordStrategyType;

public interface _PasswordGenerationStrategy {

    public String generate(PasswordOptions options);
    public PasswordStrategyType getStrategyType();
}
