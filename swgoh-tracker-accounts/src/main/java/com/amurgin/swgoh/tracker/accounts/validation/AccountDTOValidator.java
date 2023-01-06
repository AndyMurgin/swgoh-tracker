package com.amurgin.swgoh.tracker.accounts.validation;

import static java.util.Objects.isNull;

import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import com.amurgin.swgoh.tracker.accounts.records.api.PlayerSourceDTO;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AccountDTOValidator implements Validator {

  private final PlayerSourceDTOValidator playerSourceDTOValidator;

  @Autowired
  public AccountDTOValidator(PlayerSourceDTOValidator playerSourceDTOValidator) {
    this.playerSourceDTOValidator = playerSourceDTOValidator;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return AccountDTO.class == clazz;
  }

  @Override
  public void validate(Object target, Errors errors) {
    var account = (AccountDTO) target;
    validateAllyCode(account.allyCode(), errors);
    validatePlayerSources(account.playerSources(), errors);
  }

  private void validateAllyCode(Integer allyCode, Errors errors) {
    if (isNull(allyCode)) {
      errors.rejectValue("allyCode", "allyCode.empty", "Ally code is not specified");
    } else if (allyCode <= 0) {
      errors.rejectValue("allyCode", "allyCode.illegal", "Incorrect ally code value " + allyCode);
    }
  }

  private void validatePlayerSources(Collection<PlayerSourceDTO> playerSources, Errors errors) {
    if (isNull(playerSources)) {
      errors.rejectValue(
          "playerSources",
          "playerSources.empty",
          "Non-empty playerSources field should be specified");
      return;
    }

    if (playerSources.isEmpty()) {
      errors.rejectValue(
          "playerSources",
          "playerSources.empty",
          "There should be at least one player source specified");
    }

    Iterator<PlayerSourceDTO> iterator = playerSources.iterator();
    int i = -1;
    while (iterator.hasNext()) {
      i++;
      try {
        errors.pushNestedPath("playerSources[" + i + "]");
        ValidationUtils.invokeValidator(playerSourceDTOValidator, iterator.next(), errors);
      } finally {
        errors.popNestedPath();
      }
    }
  }
}
