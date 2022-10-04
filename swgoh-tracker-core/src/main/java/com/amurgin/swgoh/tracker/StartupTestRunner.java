package com.amurgin.swgoh.tracker;

import com.amurgin.swgoh.tracker.api.service.SwgohDataPullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupTestRunner implements CommandLineRunner {

    @Autowired
    private SwgohDataPullService dataPullService;

    @Value("${swgoh.player.main.allycode:0}")
    private int mainPlayerAllyCode;

    @Override
    public void run(String... args) throws Exception {
        dataPullService.getPlayerByAllyCode(mainPlayerAllyCode);
    }
}
