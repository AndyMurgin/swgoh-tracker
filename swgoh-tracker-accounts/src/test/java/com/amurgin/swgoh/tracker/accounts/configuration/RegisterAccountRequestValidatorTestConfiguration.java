package com.amurgin.swgoh.tracker.accounts.configuration;

import com.amurgin.swgoh.tracker.accounts.validation.AccountDTOValidator;
import com.amurgin.swgoh.tracker.accounts.validation.RegisterAccountRequestValidator;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class RegisterAccountRequestValidatorTestConfiguration {

  @Bean
  public AccountDTOValidator accountDTOValidator() {
    return Mockito.mock(AccountDTOValidator.class);
  }

  @Bean
  public RegisterAccountRequestValidator registerAccountRequestValidator() {
    return new RegisterAccountRequestValidator(accountDTOValidator());
  }
}
