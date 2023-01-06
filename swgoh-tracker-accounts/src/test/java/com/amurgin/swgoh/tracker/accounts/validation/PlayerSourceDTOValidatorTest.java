package com.amurgin.swgoh.tracker.accounts.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.amurgin.swgoh.tracker.accounts.model.PlayerSourceType;
import com.amurgin.swgoh.tracker.accounts.records.api.PlayerSourceDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

@ExtendWith({MockitoExtension.class})
public class PlayerSourceDTOValidatorTest {

  private static final PlayerSourceDTOValidator validator = new PlayerSourceDTOValidator();

  @Test
  public void testValidate() {
    // prepare
    var source = getPlayerSource("testid123", PlayerSourceType.TELEGRAM);
    Errors errors = new BeanPropertyBindingResult(source, "PlayerSourceDTO");

    // act
    ValidationUtils.invokeValidator(validator, source, errors);

    // verify
    assertFalse(errors.hasErrors());
  }

  @Test
  public void testValidate_nullId() {
    // prepare
    var source = getPlayerSource(null, PlayerSourceType.TELEGRAM);
    Errors errors = new BeanPropertyBindingResult(source, "PlayerSourceDTO");

    // act
    ValidationUtils.invokeValidator(validator, source, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().iterator().next();
    assertThat(
        error,
        allOf(
            hasProperty("code", is("id.empty")),
            hasProperty("defaultMessage", is("Player source ID is not specified"))));
  }

  @Test
  public void testValidate_emptyId() {
    // prepare
    var source = getPlayerSource("", PlayerSourceType.TELEGRAM);
    Errors errors = new BeanPropertyBindingResult(source, "PlayerSourceDTO");

    // act
    ValidationUtils.invokeValidator(validator, source, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().iterator().next();
    assertThat(
        error,
        allOf(
            hasProperty("code", is("id.empty")),
            hasProperty("defaultMessage", is("Player source ID is not specified"))));
  }

  @Test
  public void testValidate_nullType() {
    // prepare
    var source = getPlayerSource("testid123", null);
    Errors errors = new BeanPropertyBindingResult(source, "PlayerSourceDTO");

    // act
    ValidationUtils.invokeValidator(validator, source, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().iterator().next();
    assertThat(
        error,
        allOf(
            hasProperty("code", is("type.empty")),
            hasProperty("defaultMessage", is("Player source Type is not specified"))));
  }

  @Test
  public void testValidate_multipleErrors() {
    // prepare
    var source = getPlayerSource("", null);
    Errors errors = new BeanPropertyBindingResult(source, "PlayerSourceDTO");

    // act
    ValidationUtils.invokeValidator(validator, source, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(2, errors.getAllErrors().size());
    assertThat(
        errors.getAllErrors(),
        containsInAnyOrder(
            allOf(
                hasProperty("code", is("type.empty")),
                hasProperty("defaultMessage", is("Player source Type is not specified"))),
            allOf(
                hasProperty("code", is("id.empty")),
                hasProperty("defaultMessage", is("Player source ID is not specified")))));
  }

  private PlayerSourceDTO getPlayerSource(String id, PlayerSourceType type) {
    return PlayerSourceDTO.builder().id(id).type(type).build();
  }
}
