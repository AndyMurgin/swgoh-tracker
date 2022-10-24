package com.amurgin.swgoh.tracker.exception;

public class UnknownAllyCodeException extends RuntimeException {

  private static final String MESSAGE = "No data fetched for the provided ally code";

  public UnknownAllyCodeException() {
    super(MESSAGE);
  }

  public UnknownAllyCodeException(Integer allyCode) {
    super(MESSAGE + ": " + allyCode);
  }
}
