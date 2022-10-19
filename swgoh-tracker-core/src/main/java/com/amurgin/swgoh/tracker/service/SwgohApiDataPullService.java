package com.amurgin.swgoh.tracker.service;

import com.amurgin.swgoh.tracker.api.service.SwgohDataPullService;
import help.swgoh.api.SwgohAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class SwgohApiDataPullService implements SwgohDataPullService {

    private final SwgohAPI swgohApiConnector;

    @Autowired
    public SwgohApiDataPullService(SwgohAPI swgohApiConnector) {
        this.swgohApiConnector = swgohApiConnector;
    }

    @Override
    public Optional<String> getPlayerByAllyCode(int allyCode) {
        String playerResponse;
        try {
            playerResponse = swgohApiConnector.getPlayer(allyCode).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(playerResponse);
    }
}
