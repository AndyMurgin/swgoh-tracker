package com.amurgin.swgoh.tracker.accounts.records.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Collection;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record AccountDTO(Integer allyCode, Collection<PlayerSourceDTO> playerSources) {}
