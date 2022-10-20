package com.amurgin.swgoh.tracker.api.service;

public interface DataPullConsumer {

  void processRawData(String rawData);
}
