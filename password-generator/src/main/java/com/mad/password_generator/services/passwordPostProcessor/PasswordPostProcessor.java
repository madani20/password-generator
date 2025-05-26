package com.mad.password_generator.services.passwordPostProcessor;

import com.mad.password_generator.models.PasswordOptions;

public interface PasswordPostProcessor {

        String apply(String password, PasswordOptions passwordOptions);
}
