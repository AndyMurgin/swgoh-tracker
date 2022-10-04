package com.amurgin.swgoh.tracker.service;

import com.amurgin.swgoh.tracker.api.dto.PlayerDTO;
import com.amurgin.swgoh.tracker.api.service.SwgohDataPullService;
import help.swgoh.api.SwgohAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class SwgohApiDataPullService implements SwgohDataPullService {

    private final SwgohAPI swgohApiConnector;

    @Autowired
    public SwgohApiDataPullService(SwgohAPI swgohApiConnector) {
        this.swgohApiConnector = swgohApiConnector;
    }

    @Override
    public Optional<PlayerDTO> getPlayerByAllyCode(int allyCode) {
        String playerResponse;
        try {
            playerResponse = swgohApiConnector.getPlayer(allyCode).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(playerResponse);
        return Optional.empty();
    }
}
