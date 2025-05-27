package com.mad.password_generator.services;

import com.mad.password_generator.exceptions.StrategyNotFoundException;
import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.strategies._PasswordGenerationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class StrategyRegistry {

    @Autowired
    private final Map<String, _PasswordGenerationStrategy> strategies;

    public StrategyRegistry(Map<String, _PasswordGenerationStrategy> strategies) {
        this.strategies = strategies;
    }

    @Qualifier
    public _PasswordGenerationStrategy getStrategy(String strategyName) {
        _PasswordGenerationStrategy strategy = strategies.get(strategyName);
        if (strategy == null)
            throw new StrategyNotFoundException("Stratégie " + strategyName + " inconnue");
        return strategy;
    }


    public String generate(PasswordOptions passwordOptions) {
        return new String();
    }

}
