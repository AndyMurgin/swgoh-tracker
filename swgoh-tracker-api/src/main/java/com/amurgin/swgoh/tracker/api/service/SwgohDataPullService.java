package com.amurgin.swgoh.tracker.api.service;

import java.util.Optional;

public interface SwgohDataPullService {

  Optional<String> getPlayerByAllyCode(int allyCode);
}
