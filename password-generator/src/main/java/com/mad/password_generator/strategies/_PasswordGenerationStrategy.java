package com.mad.password_generator.strategies;

import com.mad.password_generator.models.PasswordOptions;

public interface _PasswordGenerationStrategy {

    public String generate(PasswordOptions options);
}
