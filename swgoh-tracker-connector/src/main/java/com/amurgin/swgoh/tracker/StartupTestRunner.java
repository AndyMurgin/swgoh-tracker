package com.amurgin.swgoh.tracker;

import com.amurgin.swgoh.tracker.api.service.SwgohDataRefresher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupTestRunner implements CommandLineRunner {

  @Autowired private SwgohDataRefresher swgohRefresher;

  @Override
  public void run(String... args) throws Exception {
    // TODO happen to be executed in tests and fails: swgohRefresher.run();
  }
}
