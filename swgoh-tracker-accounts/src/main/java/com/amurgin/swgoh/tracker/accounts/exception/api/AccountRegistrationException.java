package com.amurgin.swgoh.tracker.accounts.exception.api;

public class AccountRegistrationException extends RuntimeException {

  public AccountRegistrationException(String message) {
    super(message);
  }

  public AccountRegistrationException(String message, Throwable cause) {
    super(message, cause);
  }
}
