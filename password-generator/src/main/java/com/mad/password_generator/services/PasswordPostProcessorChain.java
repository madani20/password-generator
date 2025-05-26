package com.mad.password_generator.services;

import com.mad.password_generator.models.PasswordOptions;
import com.mad.password_generator.services.passwordPostProcessor.PasswordPostProcessor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordPostProcessorChain {

    private final List<PasswordPostProcessor> processors;

    public PasswordPostProcessorChain(List<PasswordPostProcessor> processors) {
        this.processors = processors;
    }

    public String apply(String password, PasswordOptions options) {
        String result = password;
        for(PasswordPostProcessor processor : processors) {
            result = processor.apply(result, options);
        }
        return result;
    }
}
