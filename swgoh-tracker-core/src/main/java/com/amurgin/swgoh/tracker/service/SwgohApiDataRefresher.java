package com.amurgin.swgoh.tracker.service;

import com.amurgin.swgoh.tracker.api.service.DataPullConsumer;
import com.amurgin.swgoh.tracker.api.service.SwgohDataPullService;
import com.amurgin.swgoh.tracker.api.service.SwgohDataRefresher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SwgohApiDataRefresher implements SwgohDataRefresher {

  private final SwgohDataPullService dataPullService;
  private final Integer mainPlayerAllyCode;
  private final List<DataPullConsumer> dataConsumers;

  @Autowired
  public SwgohApiDataRefresher(SwgohDataPullService dataPullService,
      @Value("${swgoh.player.main.allycode:0}") Integer mainPlayerAllyCode,
      List<DataPullConsumer> dataConsumers) {
    this.dataPullService = dataPullService;
    this.mainPlayerAllyCode = mainPlayerAllyCode;
    this.dataConsumers = dataConsumers;
  }

  @Override
  public void run() {
    dataPullService.getPlayerByAllyCode(mainPlayerAllyCode)
        .ifPresentOrElse(
            rawData -> dataConsumers.forEach(consumer -> consumer.processRawData(rawData)),
            () -> log.error("Data is empty for ally code {}", mainPlayerAllyCode));
  }
}