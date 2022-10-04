package com.amurgin.swgoh.tracker.api.service;

import com.amurgin.swgoh.tracker.api.dto.PlayerDTO;

import java.util.Optional;

public interface SwgohDataPullService {

    Optional<PlayerDTO> getPlayerByAllyCode(int allyCode);
}
