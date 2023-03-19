package com.amurgin.swgoh.tracker.accounts.service.connector;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SwgohApiConnectorConfiguration {

  @Value("${swgoh.api.connector.timeout:5000}")
  private int timeout;

  @Value("${swgoh.api.connector.baseurl:'http://localhost:8080'}")
  private String rootPath;

  @Bean
  public SwgohApiConnectorService swgohApiConnectorService() {
    return new SwgohApiConnectorService(swgohApiRestTemplate(), rootPath);
  }

  @Bean
  public RestTemplate swgohApiRestTemplate() {
    return new RestTemplate(getClientHttpRequestFactory());
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory() {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
        new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(timeout);
    return clientHttpRequestFactory;
  }
}
