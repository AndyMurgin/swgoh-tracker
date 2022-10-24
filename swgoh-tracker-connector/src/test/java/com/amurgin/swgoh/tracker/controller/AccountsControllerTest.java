package com.amurgin.swgoh.tracker.controller;

import com.amurgin.swgoh.tracker.SwgohTrackerApplication;
import help.swgoh.api.SwgohAPI;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = SwgohTrackerApplication.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountsControllerTest {

  @TestConfiguration
  static class AccountsControllerTestConfiguration {

    @Bean
    public SwgohAPI swgohApiConnector() {
      return Mockito.mock(SwgohAPI.class);
    }
  }

  @Autowired private MockMvc mockMvc;

  @MockBean private SwgohAPI swgohApiConnector;

  @Test
  public void test() {}
}
