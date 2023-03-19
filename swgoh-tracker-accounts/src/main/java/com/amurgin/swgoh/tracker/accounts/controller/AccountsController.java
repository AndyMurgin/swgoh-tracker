package com.amurgin.swgoh.tracker.accounts.controller;

import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountRequest;
import com.amurgin.swgoh.tracker.accounts.records.api.register.RegisterAccountResponse;
import com.amurgin.swgoh.tracker.accounts.service.api.AccountsApiValidationService;
import com.amurgin.swgoh.tracker.accounts.service.registration.AccountRegistrationService;
import com.amurgin.swgoh.tracker.accounts.validation.RegisterAccountRequestValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/accounts/*")
public class AccountsController {

  private final RegisterAccountRequestValidator registerAccountRequestValidator;
  private final AccountsApiValidationService validationService;
  private final AccountRegistrationService registrationService;

  @PostMapping(
      value = "/register",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public RegisterAccountResponse register(
      @RequestBody @Validated RegisterAccountRequest request, BindingResult bindingResult) {
    validationService.throwIfValidationFailed(bindingResult, request);
    registrationService.register(request);

    // TODO rework to return values got after registration
    return RegisterAccountResponse.builder()
        .uuid(request.uuid())
        .accountId(1L)
        .accountName("TestAccount")
        .accountGalacticPower(1)
        .build();
  }

  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.addValidators(registerAccountRequestValidator);
  }
}
