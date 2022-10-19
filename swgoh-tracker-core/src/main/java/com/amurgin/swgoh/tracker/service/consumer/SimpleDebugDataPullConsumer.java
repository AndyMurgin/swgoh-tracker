package com.amurgin.swgoh.tracker.service.consumer;

import com.amurgin.swgoh.tracker.api.service.DataPullConsumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleDebugDataPullConsumer implements DataPullConsumer {

    @Override
    public void processRawData(String rawData) {
        log.debug(rawData);
    }
}