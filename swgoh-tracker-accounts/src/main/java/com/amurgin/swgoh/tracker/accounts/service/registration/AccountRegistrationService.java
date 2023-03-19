package com.amurgin.swgoh.tracker.accounts.service.registration;

import com.amurgin.swgoh.tracker.accounts.exception.api.AccountRegistrationException;
import com.amurgin.swgoh.tracker.accounts.exception.connector.TrackerAppApiException;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
import com.amurgin.swgoh.tracker.accounts.service.connector.SwgohApiConnectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountRegistrationService {

  private final SwgohApiConnectorService connectorService;

  public String register(RegisterAccountRequest request) {
    String accountInfo = getAccountInfo(request.accountInfo().allyCode());
    // TODO save in Mongo and return the saved entity
    return accountInfo;
  }

  private String getAccountInfo(Integer allycode) {
    try {
      return connectorService.getAccount(allycode);
    } catch (TrackerAppApiException e) {
      throw new AccountRegistrationException(
          String.format(
              "Error during account registration for allycode %d : %s", allycode, e.getMessage()),
          e);
    }
  }
}
