package com.amurgin.swgoh.tracker.accounts.records.api.register;

import com.amurgin.swgoh.tracker.accounts.records.api.AccountDTO;
import lombok.Builder;

@Builder
public record RegisterAccountRequest(String uuid, AccountDTO accountInfo) {}
