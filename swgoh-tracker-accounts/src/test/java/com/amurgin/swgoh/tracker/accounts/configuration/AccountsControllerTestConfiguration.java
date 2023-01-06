package com.amurgin.swgoh.tracker.accounts.configuration;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan({
  "com.amurgin.swgoh.tracker.accounts.service.api",
  "com.amurgin.swgoh.tracker.accounts.validation"
})
public class AccountsControllerTestConfiguration {}
