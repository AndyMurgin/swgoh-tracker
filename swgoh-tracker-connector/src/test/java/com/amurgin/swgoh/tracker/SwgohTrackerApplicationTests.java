package com.amurgin.swgoh.tracker;

import com.amurgin.swgoh.tracker.SwgohTrackerApplicationTests.SwgohTrackerApplicationTestsConfiguration;
import help.swgoh.api.SwgohAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(
    classes = {SwgohTrackerApplication.class, SwgohTrackerApplicationTestsConfiguration.class})
@ActiveProfiles("tests")
class SwgohTrackerApplicationTests {

  // TODO make a common tests configuration
  @TestConfiguration
  static class SwgohTrackerApplicationTestsConfiguration {

    @Bean
    public SwgohAPI swgohApiConnector() {
      return Mockito.mock(SwgohAPI.class);
    }
  }

  @Test
  void contextLoads() {}
}
