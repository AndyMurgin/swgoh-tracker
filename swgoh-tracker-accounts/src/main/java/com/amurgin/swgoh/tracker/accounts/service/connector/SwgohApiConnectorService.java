package com.amurgin.swgoh.tracker.accounts.service.connector;

import com.amurgin.swgoh.tracker.accounts.exception.connector.TrackerAppApiException;
import java.util.Collections;
import org.springframework.web.client.RestTemplate;

public class SwgohApiConnectorService {

  private RestTemplate restTemplate;
  private String rootPath;

  public SwgohApiConnectorService(RestTemplate restTemplate, String rootPath) {
    this.restTemplate = restTemplate;
    this.rootPath = rootPath;
  }

  public String getAccount(Integer allycode) throws TrackerAppApiException {
    String responseBody;
    try {
      responseBody =
          restTemplate.getForObject(
              rootPath + "/accounts/{allyCode}",
              String.class,
              Collections.singletonMap("allyCode", allycode));
    } catch (Exception e) {
      throw new TrackerAppApiException(
          "Error during connecting to Tracker App API: " + e.getMessage(), e);
    }

    if (responseBody == null) {
      throw new TrackerAppApiException(
          String.format("Bad data received from Tracker App for allyCode %d : null", allycode));
    }
    return responseBody;
  }
}
