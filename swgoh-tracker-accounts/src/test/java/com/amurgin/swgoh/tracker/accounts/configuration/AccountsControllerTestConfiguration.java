package com.amurgin.swgoh.tracker.accounts.configuration;

import static org.mockito.Mockito.mock;

import com.amurgin.swgoh.tracker.accounts.service.connector.SwgohApiConnectorService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan({
  "com.amurgin.swgoh.tracker.accounts.service.api",
  "com.amurgin.swgoh.tracker.accounts.validation",
  "com.amurgin.swgoh.tracker.accounts.service.registration"
})
public class AccountsControllerTestConfiguration {

  @Bean
  public SwgohApiConnectorService connectorService() {
    return mock(SwgohApiConnectorService.class);
  }
}
