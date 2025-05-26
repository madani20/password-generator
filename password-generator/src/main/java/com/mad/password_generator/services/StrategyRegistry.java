package com.mad.password_generator.services;

import com.mad.password_generator.exceptions.StrategyNotFoundException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.strategies._PasswordGenerationStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StrategyRegistry {

    private final Map<String, _PasswordGenerationStrategy> strategies;

    public StrategyRegistry(Map<String, _PasswordGenerationStrategy> strategies) {
        this.strategies = strategies;
    }

    public String generate(PasswordOptions passwordOptions) {
        return new String();
    }

    @Qualifier
    public _PasswordGenerationStrategy getStrategy(String strategyName) {
        _PasswordGenerationStrategy strategy = strategies.get(strategyName);
        if (strategy == null)
            throw new StrategyNotFoundException(strategyName);
        return strategy;
    }
}
