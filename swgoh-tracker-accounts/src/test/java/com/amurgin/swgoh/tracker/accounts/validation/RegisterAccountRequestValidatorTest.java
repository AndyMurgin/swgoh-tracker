package com.amurgin.swgoh.tracker.accounts.validation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;

import com.amurgin.swgoh.tracker.accounts.configuration.RegisterAccountRequestValidatorTestConfiguration;
import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
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
@ContextConfiguration(classes = {RegisterAccountRequestValidatorTestConfiguration.class})
public class RegisterAccountRequestValidatorTest {

  @Autowired public RegisterAccountRequestValidator registerAccountRequestValidator;

  // mocks
  @Autowired public AccountDTOValidator accountDTOValidator;

  @BeforeEach
  public void setUp() {
    mockAccountValidator();
  }

  @Test
  public void testValidate() {
    // prepare
    var request = getRequest("testValidate", AccountInfoTestMode.PROPER);
    Errors errors = new BeanPropertyBindingResult(request, "RegisterAccountRequest");

    // act
    ValidationUtils.invokeValidator(registerAccountRequestValidator, request, errors);

    // verify
    assertFalse(errors.hasErrors());
  }

  @Test
  public void testValidate_nullUuid() {
    // prepare
    var request = getRequest(null, AccountInfoTestMode.PROPER);
    Errors errors = new BeanPropertyBindingResult(request, "RegisterAccountRequest");

    // act
    ValidationUtils.invokeValidator(registerAccountRequestValidator, request, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().iterator().next();
    assertThat(
        error,
        allOf(
            hasProperty("code", is("uuid.empty")),
            hasProperty("defaultMessage", is("Input requests must have uuid set"))));
  }

  @Test
  public void testValidate_emptyUuid() {
    // prepare
    var request = getRequest("", AccountInfoTestMode.PROPER);
    Errors errors = new BeanPropertyBindingResult(request, "RegisterAccountRequest");

    // act
    ValidationUtils.invokeValidator(registerAccountRequestValidator, request, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().iterator().next();
    assertThat(
        error,
        allOf(
            hasProperty("code", is("uuid.empty")),
            hasProperty("defaultMessage", is("Input requests must have uuid set"))));
  }

  @Test
  public void testValidate_nullAccountInfo() {
    // prepare
    var request = getRequest("testValidate_nullAccountInfo", AccountInfoTestMode.MISSED);
    Errors errors = new BeanPropertyBindingResult(request, "RegisterAccountRequest");

    // act
    ValidationUtils.invokeValidator(registerAccountRequestValidator, request, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(1, errors.getAllErrors().size());
    ObjectError error = errors.getAllErrors().iterator().next();
    assertThat(
        error,
        allOf(
            hasProperty("code", is("accountInfo.empty")),
            hasProperty(
                "defaultMessage", is("Non-empty account info is required for registration"))));
  }

  @Test
  public void testValidate_multipleErrors() {
    // prepare
    var request = getRequest("", AccountInfoTestMode.MISSED);
    Errors errors = new BeanPropertyBindingResult(request, "RegisterAccountRequest");

    // act
    ValidationUtils.invokeValidator(registerAccountRequestValidator, request, errors);

    // verify
    assertTrue(errors.hasErrors());
    assertEquals(2, errors.getAllErrors().size());
    assertThat(
        errors.getAllErrors(),
        containsInAnyOrder(
            allOf(
                hasProperty("code", is("accountInfo.empty")),
                hasProperty(
                    "defaultMessage", is("Non-empty account info is required for registration"))),
            allOf(
                hasProperty("code", is("uuid.empty")),
                hasProperty("defaultMessage", is("Input requests must have uuid set")))));
  }

  private RegisterAccountRequest getRequest(String uuid, AccountInfoTestMode accountInfoTestMode) {
    return RegisterAccountRequest.builder()
        .uuid(uuid)
        .accountInfo(getAccountInfo(accountInfoTestMode))
        .build();
  }

  private AccountDTO getAccountInfo(AccountInfoTestMode accountInfoTestMode) {
    return switch (accountInfoTestMode) {
      case PROPER -> AccountDTO.builder().build();
      case MISSED -> null;
    };
  }

  private void mockAccountValidator() {
    lenient().when(accountDTOValidator.supports(AccountDTO.class)).thenReturn(true);
  }

  private enum AccountInfoTestMode {
    PROPER,
    MISSED
  }
}
