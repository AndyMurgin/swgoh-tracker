package com.amurgin.swgoh.tracker.configuration;

import com.amurgin.swgoh.tracker.api.service.SwgohDataPullService;
import com.amurgin.swgoh.tracker.service.SwgohApiDataPullService;
import help.swgoh.api.SwgohAPI;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class SwgohApiDataPullServiceConfiguration {

  @Bean
  public SwgohDataPullService dataPullService(SwgohAPI swgohApiConnector) {
    return new SwgohApiDataPullService(swgohApiConnector);
  }
}
