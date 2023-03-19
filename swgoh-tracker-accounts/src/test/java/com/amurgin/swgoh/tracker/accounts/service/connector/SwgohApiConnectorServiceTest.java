package com.amurgin.swgoh.tracker.accounts.service.connector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.amurgin.swgoh.tracker.accounts.exception.connector.TrackerAppApiException;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
public class SwgohApiConnectorServiceTest {

  private SwgohApiConnectorService connectorService;

  // Mocks
  @Mock private RestTemplate restTemplate;

  @BeforeEach
  public void setUp() {
    connectorService = new SwgohApiConnectorService(restTemplate, "http://localhost:8080");
  }

  @Test
  public void test_getAccount() throws TrackerAppApiException {
    // prepare
    var responseBody = "{accountInfo}";
    stubRestTemplate(responseBody);

    // act
    String responseActual = connectorService.getAccount(123);

    // verify
    assertEquals(responseBody, responseActual);
  }

  @Test
  public void test_getAccount_nullResponse() {
    // prepare
    stubRestTemplate(null);

    // act & verify
    TrackerAppApiException exception =
        assertThrows(TrackerAppApiException.class, () -> connectorService.getAccount(123));
    assertEquals(
        "Bad data received from Tracker App for allyCode 123 : null", exception.getMessage());
  }

  @Test
  public void test_getAccount_apiException() {
    // prepare
    stubRestTemplateFail();

    // act & verify
    TrackerAppApiException exception =
        assertThrows(TrackerAppApiException.class, () -> connectorService.getAccount(123));
    assertEquals("Error during connecting to Tracker App API: null", exception.getMessage());
  }

  private void stubRestTemplate(String responseBody) {
    when(restTemplate.getForObject(anyString(), eq(String.class), any(Map.class)))
        .thenReturn(responseBody);
  }

  private void stubRestTemplateFail() {
    when(restTemplate.getForObject(anyString(), eq(String.class), any(Map.class)))
        .thenThrow(new RuntimeException());
  }
}
