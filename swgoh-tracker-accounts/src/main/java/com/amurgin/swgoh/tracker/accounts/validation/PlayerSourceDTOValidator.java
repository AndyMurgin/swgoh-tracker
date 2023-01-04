package com.amurgin.swgoh.tracker.accounts.validation;

import static java.util.Objects.isNull;

import com.amurgin.swgoh.tracker.accounts.records.api.PlayerSourceDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PlayerSourceDTOValidator implements Validator {

  @Override
  public boolean supports(Class<?> clazz) {
    return PlayerSourceDTO.class == clazz;
  }

  @Override
  public void validate(Object target, Errors errors) {
    var playerSource = (PlayerSourceDTO) target;
    if (isEmpty(playerSource.id())) {
      errors.rejectValue("id", "id.empty", "Player source ID is not specified");
    }
    if (isNull(playerSource.type())) {
      errors.rejectValue("type", "type.empty", "Player source Type is not specified");
    }
  }

  private boolean isEmpty(String str) {
    return !StringUtils.hasText(str);
  }
}
