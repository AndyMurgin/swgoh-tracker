package com.amurgin.swgoh.tracker.service.consumer;

import com.amurgin.swgoh.tracker.api.service.DataPullConsumer;
import org.springframework.stereotype.Component;

@Component
public class SimpleDebugDataPullConsumer implements DataPullConsumer {

    @Override
    public void processRawData(String rawData) {
        System.out.println(rawData);
    }
}