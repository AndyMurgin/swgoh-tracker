package com.amurgin.swgoh.tracker.configuration;

import help.swgoh.api.SwgohAPI;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SwgohAPIMockTestConfiguration {

  @Bean
  public SwgohAPI swgohApiConnector() {
    return Mockito.mock(SwgohAPI.class);
  }
}
