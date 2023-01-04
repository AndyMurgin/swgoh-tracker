package com.amurgin.swgoh.tracker.controller;

import com.amurgin.swgoh.tracker.api.service.SwgohDataPullService;
import com.amurgin.swgoh.tracker.exception.IllegalAllyCodeException;
import com.amurgin.swgoh.tracker.exception.UnknownAllyCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts/*")
public class AccountsController {

  private final SwgohDataPullService dataPullService;

  @Autowired
  public AccountsController(SwgohDataPullService dataPullService) {
    this.dataPullService = dataPullService;
  }

  @GetMapping("/{allyCode}")
  public String fetchByAllyCode(@PathVariable(value = "allyCode") Integer allyCode) {
    if (allyCode == null || allyCode <= 0) {
      throw new IllegalAllyCodeException(allyCode);
    }

    return dataPullService
        .getPlayerByAllyCode(allyCode)
        .orElseThrow(() -> new UnknownAllyCodeException(allyCode));
  }
}
