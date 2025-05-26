package com.mad.password_generator.services.passwordPostProcessor;

import com.mad.password_generator.models.PasswordOptions;

public class PrefixSuffixPostProcessor implements PasswordPostProcessor {

    @Override
    public String apply(String password, PasswordOptions passwordOptions) {
        String prefix = passwordOptions.getPrefix() != null ? passwordOptions.getPrefix() : "";
        String suffix = passwordOptions.getSuffix() != null ? passwordOptions.getSuffix() : "";
        return prefix + password + suffix;
    }
}
