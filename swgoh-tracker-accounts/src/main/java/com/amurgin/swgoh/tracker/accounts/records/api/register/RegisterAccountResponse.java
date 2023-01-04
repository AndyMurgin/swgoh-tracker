package com.amurgin.swgoh.tracker.accounts.records.api.register;

import lombok.Builder;

@Builder
public record RegisterAccountResponse(
    String uuid, long accountId, String accountName, Integer accountGalacticPower) {}
