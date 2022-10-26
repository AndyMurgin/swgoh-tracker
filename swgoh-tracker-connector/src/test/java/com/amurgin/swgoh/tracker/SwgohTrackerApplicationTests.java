package com.amurgin.swgoh.tracker;

import com.amurgin.swgoh.tracker.configuration.SwgohAPIMockTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(
    classes = {SwgohTrackerApplication.class, SwgohAPIMockTestConfiguration.class})
@ActiveProfiles("tests")
class SwgohTrackerApplicationTests {

  @Test
  void contextLoads() {}
}
