package com.amurgin.swgoh.tracker.accounts.records.api.register;

import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
public record RegisterAccountRequest(String uuid, AccountDTO accountInfo) {}
