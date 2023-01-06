package com.amurgin.swgoh.tracker.accounts.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.amurgin.swgoh.tracker.accounts.configuration.AccountDTOValidatorTestConfiguration;
import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import com.amurgin.swgoh.tracker.accounts.records.api.PlayerSourceDTO;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {AccountDTOValidatorTestConfiguration.class})
public class AccountDTOValidatorTest {

  @Autowired private AccountDTOValidator accountDTOValidator;

  // mocks
  @Autowired private PlayerSourceDTOValidator playerSourceDTOValidator;

  @BeforeEach
  public void setUp() {
    mockPlayerSourceValidator();
  }

  @Test
  public void testValidate() {
    // prepare
    AccountDTO accountDTO = getProperAccount(1);
    Errors errors = new BeanPropertyBindingResult(accountDTO, "AccountDTO");

    // act
    ValidationUtils.invokeValidator(accountDTOValidator, accountDTO, errors);

    // verify
    assertFalse(errors.hasErrors());
  }

  @Test
  public void testValidate_noAllyCode() {
    // prepare
    AccountDTO accountDTO = getProperAccount(null);
    Errors errors = new BeanPropertyBindingResult(accountDTO, "AccountDTO");

    // act
    ValidationUtils.invokeValidator(accountDTOValidator, accountDTO, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().get(0);
    assertEquals("allyCode.empty", error.getCode());
    assertEquals("Ally code is not specified", error.getDefaultMessage());
  }

  @Test
  public void testValidate_negativeAllyCode() {
    // prepare
    AccountDTO accountDTO = getProperAccount(-1);
    Errors errors = new BeanPropertyBindingResult(accountDTO, "AccountDTO");

    // act
    ValidationUtils.invokeValidator(accountDTOValidator, accountDTO, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().get(0);
    assertEquals("allyCode.illegal", error.getCode());
    assertEquals("Incorrect ally code value -1", error.getDefaultMessage());
  }

  @Test
  public void testValidate_noPlayerSource() {
    // prepare
    AccountDTO accountDTO = getAccountWithoutSources(1);
    Errors errors = new BeanPropertyBindingResult(accountDTO, "AccountDTO");

    // act
    ValidationUtils.invokeValidator(accountDTOValidator, accountDTO, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().get(0);
    assertEquals("playerSources.empty", error.getCode());
    assertEquals("Non-empty playerSources field should be specified", error.getDefaultMessage());
  }

  @Test
  public void testValidate_emptyPlayerSources() {
    // prepare
    AccountDTO accountDTO = getAccountWithEmptySources(1);
    Errors errors = new BeanPropertyBindingResult(accountDTO, "AccountDTO");

    // act
    ValidationUtils.invokeValidator(accountDTOValidator, accountDTO, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().get(0);
    assertEquals("playerSources.empty", error.getCode());
    assertEquals("There should be at least one player source specified", error.getDefaultMessage());
  }

  @Test
  public void testValidate_multipleErrors() {
    // prepare
    AccountDTO accountDTO = getAccountWithEmptySources(-1);
    Errors errors = new BeanPropertyBindingResult(accountDTO, "AccountDTO");

    // act
    ValidationUtils.invokeValidator(accountDTOValidator, accountDTO, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(2, errors.getAllErrors().size());
    assertThat(
        errors.getAllErrors(),
        containsInAnyOrder(
            allOf(
                hasProperty("code", is("allyCode.illegal")),
                hasProperty("defaultMessage", is("Incorrect ally code value -1"))),
            allOf(
                hasProperty("code", is("playerSources.empty")),
                hasProperty(
                    "defaultMessage",
                    is("There should be at least one player source specified")))));
  }

  private AccountDTO getProperAccount(Integer allyCode) {
    return getAccount(allyCode, PlayerSourceTestMode.PROPER);
  }

  private AccountDTO getAccountWithoutSources(Integer allyCode) {
    return getAccount(allyCode, PlayerSourceTestMode.MISSED);
  }

  private AccountDTO getAccountWithEmptySources(Integer allyCode) {
    return getAccount(allyCode, PlayerSourceTestMode.EMPTY);
  }

  private AccountDTO getAccount(Integer allyCode, PlayerSourceTestMode playerSourceMode) {
    return AccountDTO.builder()
        .allyCode(allyCode)
        .playerSources(getProperPlayerSources(playerSourceMode))
        .build();
  }

  private Collection<PlayerSourceDTO> getProperPlayerSources(
      PlayerSourceTestMode playerSourceMode) {
    return switch (playerSourceMode) {
      case EMPTY -> Collections.emptyList();
      case MISSED -> null;
      case PROPER -> Collections.singletonList(PlayerSourceDTO.builder().build());
    };
  }

  private void mockPlayerSourceValidator() {
    when(playerSourceDTOValidator.supports(PlayerSourceDTO.class)).thenReturn(true);
  }

  private enum PlayerSourceTestMode {
    PROPER,
    MISSED,
    EMPTY
  }
}
