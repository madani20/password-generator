package com.mad.password_generator.services;

import com.mad.password_generator.models.PasswordStrategyType;
import com.mad.password_generator.strategies._PasswordGenerationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class PasswordGenerationStrategyRegistry {
    private static final Logger logger = LoggerFactory.getLogger(PasswordGenerationStrategyRegistry.class);

    private final Map<PasswordStrategyType, _PasswordGenerationStrategy> strategies = new EnumMap<>(PasswordStrategyType.class);

    @Autowired
    public PasswordGenerationStrategyRegistry(List<_PasswordGenerationStrategy> strategiesList) {
        for (_PasswordGenerationStrategy strategy : strategiesList) {
            strategies.put(strategy.getStrategyType(), strategy);
        }
    }

    public _PasswordGenerationStrategy getStrategy(PasswordStrategyType strategyType) {
    logger.info("Init getStrategy()");

        _PasswordGenerationStrategy strategy = strategies.get(strategyType);

        logger.info("Clés disponibles dans strategies: {}", strategies.keySet());
        logger.info("Fin getStrategy()");
        return strategy;
    }
}
