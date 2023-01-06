package com.amurgin.swgoh.tracker.accounts.records.api;

import com.amurgin.swgoh.tracker.accounts.model.PlayerSourceType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record PlayerSourceDTO(String id, PlayerSourceType type) {}
