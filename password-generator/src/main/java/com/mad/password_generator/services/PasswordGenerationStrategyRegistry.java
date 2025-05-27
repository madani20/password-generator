package com.mad.password_generator.services;

import com.mad.password_generator.exceptions.StrategyNotFoundException;
import com.mad.password_generator.strategies._PasswordGenerationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PasswordGenerationStrategyRegistry {
    private static final Logger logger = LoggerFactory.getLogger(PasswordGenerationStrategyRegistry.class);

    private final Map<String, _PasswordGenerationStrategy> strategies;

    public PasswordGenerationStrategyRegistry(Map<String, _PasswordGenerationStrategy> strategies) {
        this.strategies = strategies;
    }

    public _PasswordGenerationStrategy getStrategy(String strategyName) {
    logger.info("Init getStrategy()");

        _PasswordGenerationStrategy strategy = strategies.get(strategyName);
        logger.info("Clés disponibles dans strategies: {}", strategies.keySet());
        if (strategy == null)
            throw new StrategyNotFoundException("Stratégie '" + strategyName + "' inconnue");

        logger.info("Fin getStrategy()");
        return strategy;
    }

}
