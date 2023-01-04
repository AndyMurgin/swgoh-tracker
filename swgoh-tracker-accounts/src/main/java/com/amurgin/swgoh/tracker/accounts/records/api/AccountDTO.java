package com.amurgin.swgoh.tracker.accounts.records.api;

import java.util.Collection;
import lombok.Builder;

@Builder
public record AccountDTO(Integer allyCode, Collection<PlayerSourceDTO> playerSources) {}
