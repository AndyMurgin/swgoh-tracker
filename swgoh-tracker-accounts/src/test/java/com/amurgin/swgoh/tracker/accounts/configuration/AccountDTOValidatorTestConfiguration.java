package com.amurgin.swgoh.tracker.accounts.configuration;

import com.amurgin.swgoh.tracker.accounts.validation.AccountDTOValidator;
import com.amurgin.swgoh.tracker.accounts.validation.PlayerSourceDTOValidator;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class AccountDTOValidatorTestConfiguration {

  @Bean
  public PlayerSourceDTOValidator playerSourceDTOValidator() {
    return Mockito.mock(PlayerSourceDTOValidator.class);
  }

  @Bean
  public AccountDTOValidator accountDTOValidator() {
    return new AccountDTOValidator(playerSourceDTOValidator());
  }
}
