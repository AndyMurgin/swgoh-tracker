package com.amurgin.swgoh.tracker.accounts.records.api;

import com.amurgin.swgoh.tracker.accounts.model.PlayerSourceType;
import lombok.Builder;

@Builder
public record PlayerSourceDTO(String id, PlayerSourceType type) {}
