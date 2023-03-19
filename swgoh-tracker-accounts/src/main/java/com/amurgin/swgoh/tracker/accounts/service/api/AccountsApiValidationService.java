package com.amurgin.swgoh.tracker.accounts.service.api;

import com.amurgin.swgoh.tracker.accounts.exception.api.IncorrectRegisterInfoException;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Slf4j
@Service
public class AccountsApiValidationService {

  public void throwIfValidationFailed(
      BindingResult bindingResult, RegisterAccountRequest registerAccountRequest) {
    if (bindingResult.hasErrors()) {
      log.info("Input account validation error - request {}", registerAccountRequest.uuid());
      throw new IncorrectRegisterInfoException(getBindingErrorMessage(bindingResult));
    }
  }

  private String getBindingErrorMessage(BindingResult bindingResult) {
    return bindingResult.getAllErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(" | "));
  }
}
