package com.amurgin.swgoh.tracker.accounts.exception.connector;

public class TrackerAppApiException extends Exception {

  public TrackerAppApiException(String message) {
    super(message);
  }

  public TrackerAppApiException(String message, Throwable cause) {
    super(message, cause);
  }
}
