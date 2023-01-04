package com.amurgin.swgoh.tracker.accounts.validation;

import static java.util.Objects.isNull;

import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class RegisterAccountRequestValidator implements Validator {

  private final AccountDTOValidator accountDTOValidator;

  @Autowired
  public RegisterAccountRequestValidator(AccountDTOValidator accountDTOValidator) {
    this.accountDTOValidator = accountDTOValidator;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return RegisterAccountRequest.class == clazz;
  }

  @Override
  public void validate(Object target, Errors errors) {
    var request = (RegisterAccountRequest) target;
    if (isEmpty(request.uuid())) {
      errors.rejectValue("uuid", "uuid.empty", "Input requests must have uuid set");
    }
    validateAccountInfo(request.accountInfo(), errors);
  }

  private void validateAccountInfo(AccountDTO accountInfo, Errors errors) {
    if (isNull(accountInfo)) {
      errors.rejectValue(
          "accountInfo",
          "accountInfo.empty",
          "Non-empty account info is required for registration");
    }

    try {
      errors.pushNestedPath("accountInfo");
      ValidationUtils.invokeValidator(accountDTOValidator, accountInfo, errors);
    } finally {
      errors.popNestedPath();
    }
  }

  private boolean isEmpty(String str) {
    return !StringUtils.hasText(str);
  }
}
