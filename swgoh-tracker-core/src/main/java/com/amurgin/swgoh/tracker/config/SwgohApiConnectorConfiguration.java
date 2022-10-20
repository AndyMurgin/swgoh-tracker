package com.amurgin.swgoh.tracker.config;

import help.swgoh.api.SwgohAPI;
import help.swgoh.api.SwgohAPIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwgohApiConnectorConfiguration {
  @Bean
  public SwgohAPI swgohApiConnector(
      @Value("${swgoh.api.username:username}") String username,
      @Value("${swgoh.api.password:password}") String password) {
    return new SwgohAPIBuilder().withUsername(username).withPassword(password).build();
  }
}
