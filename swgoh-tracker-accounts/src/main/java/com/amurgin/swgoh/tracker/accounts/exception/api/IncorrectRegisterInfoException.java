package com.amurgin.swgoh.tracker.accounts.exception.api;

public class IncorrectRegisterInfoException extends RuntimeException {

  private static final String MESSAGE = "Unable to register the account";

  public IncorrectRegisterInfoException(String message) {
    super(MESSAGE + " : " + message);
  }
}
