package com.amurgin.swgoh.tracker.accounts.service.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.amurgin.swgoh.tracker.accounts.exception.IncorrectRegisterInfoException;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

@ExtendWith(MockitoExtension.class)
public class AccountsApiValidationServiceTest {

  private final AccountsApiValidationService service = new AccountsApiValidationService();

  @Test
  public void testThrowIfValidationFailed_noError() {
    // prepare
    BindingResult bindingResult = getSucceedResult();
    RegisterAccountRequest request = getRequest("testThrowIfValidationFailed_noError");

    // act
    service.throwIfValidationFailed(bindingResult, request);
  }

  @Test
  public void testThrowIfValidationFailed_oneError() {
    // prepare
    BindingResult bindingResult = getFailedResult(Collections.singletonList("Error 1"));
    RegisterAccountRequest request = getRequest("testThrowIfValidationFailed_oneError");

    // act
    IncorrectRegisterInfoException exception =
        Assertions.assertThrows(
            IncorrectRegisterInfoException.class,
            () -> service.throwIfValidationFailed(bindingResult, request));

    // verify
    assertEquals("Unable to register the account : Error 1", exception.getMessage());
  }

  @Test
  public void testThrowIfValidationFailed_multipleErrors() {
    // prepare
    BindingResult bindingResult = getFailedResult(Arrays.asList("Error 1", "Error 2"));
    RegisterAccountRequest request = getRequest("testThrowIfValidationFailed_multipleErrors");

    // act
    IncorrectRegisterInfoException exception =
        Assertions.assertThrows(
            IncorrectRegisterInfoException.class,
            () -> service.throwIfValidationFailed(bindingResult, request));

    // verify
    assertEquals("Unable to register the account : Error 1 | Error 2", exception.getMessage());
  }

  private BindingResult getSucceedResult() {
    return getBindingResult(true, Collections.emptyList());
  }

  private BindingResult getFailedResult(Collection<String> errors) {
    return getBindingResult(false, errors);
  }

  private BindingResult getBindingResult(boolean isSucceed, Collection<String> errors) {
    BindingResult bindingResult = mock(BindingResult.class);
    when(bindingResult.hasErrors()).thenReturn(!isSucceed);
    lenient().when(bindingResult.getAllErrors()).thenReturn(getValidationErrors(errors));
    return bindingResult;
  }

  private List<ObjectError> getValidationErrors(Collection<String> errorMessages) {
    return errorMessages.stream().map(this::getObjectError).toList();
  }

  private ObjectError getObjectError(String message) {
    return new ObjectError("request", message);
  }

  private RegisterAccountRequest getRequest(String uuid) {
    return RegisterAccountRequest.builder().uuid(uuid).build();
  }
}
