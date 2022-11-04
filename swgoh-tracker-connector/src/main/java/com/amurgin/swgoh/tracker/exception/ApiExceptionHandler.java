package com.amurgin.swgoh.tracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value = {IllegalAllyCodeException.class})
  protected ResponseEntity<Object> handleConflict(IllegalAllyCodeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @ExceptionHandler(value = {UnknownAllyCodeException.class})
  protected ResponseEntity<Object> handleConflict(UnknownAllyCodeException ex, WebRequest request) {
    return handleExceptionInternal(
        ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
  }
}
