package com.amurgin.swgoh.tracker.exception;

public class IllegalAllyCodeException extends RuntimeException {

  private static final String MESSAGE = "Unable to process ally code";

  public IllegalAllyCodeException() {
    super(MESSAGE);
  }

  public IllegalAllyCodeException(Integer allyCode) {
    super(MESSAGE + ": " + allyCode);
  }
}
