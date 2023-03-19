package com.amurgin.swgoh.tracker.accounts.service.registration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.amurgin.swgoh.tracker.accounts.exception.api.AccountRegistrationException;
import com.amurgin.swgoh.tracker.accounts.exception.connector.TrackerAppApiException;
import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
import com.amurgin.swgoh.tracker.accounts.service.connector.SwgohApiConnectorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountRegistrationServiceTest {

  private AccountRegistrationService service;

  // Mocks
  @Mock private SwgohApiConnectorService apiConnectorService;

  @BeforeEach
  public void setUp() {
    service = new AccountRegistrationService(apiConnectorService);
  }

  @Test
  public void test_register() throws TrackerAppApiException {
    // prepare
    var allyCode = 123;
    var returnedAccount = "Test Account Info JSON";
    RegisterAccountRequest request = getRequest(allyCode);
    stubConnector(allyCode, returnedAccount);

    // act
    String registerInfo = service.register(request);

    // verify
    verify(apiConnectorService, times(1)).getAccount(allyCode);
    assertEquals(returnedAccount, registerInfo);
  }

  @Test
  public void test_register_apiException() throws TrackerAppApiException {
    // prepare
    var allyCode = 123;
    RegisterAccountRequest request = getRequest(allyCode);
    stubConnectorFailed(allyCode);

    // act & verify
    AccountRegistrationException exception =
        assertThrows(AccountRegistrationException.class, () -> service.register(request));
    assertEquals(
        "Error during account registration for allycode 123 : null", exception.getMessage());
  }

  private RegisterAccountRequest getRequest(Integer allyCode) {
    RegisterAccountRequest request = mock(RegisterAccountRequest.class);
    AccountDTO accountDTO = mock(AccountDTO.class);
    when(request.accountInfo()).thenReturn(accountDTO);
    when(accountDTO.allyCode()).thenReturn(allyCode);
    return request;
  }

  private void stubConnector(Integer allyCode, String returnedAccount)
      throws TrackerAppApiException {
    when(apiConnectorService.getAccount(allyCode)).thenReturn(returnedAccount);
  }

  private void stubConnectorFailed(Integer allyCode) throws TrackerAppApiException {
    when(apiConnectorService.getAccount(allyCode)).thenThrow(TrackerAppApiException.class);
  }
}
