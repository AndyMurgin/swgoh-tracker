package com.amurgin.swgoh.tracker.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amurgin.swgoh.tracker.SwgohApiCommonTests;
import com.amurgin.swgoh.tracker.configuration.SwgohAPIMockTestConfiguration;
import com.amurgin.swgoh.tracker.configuration.SwgohApiDataPullServiceConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(
    classes = {SwgohAPIMockTestConfiguration.class, SwgohApiDataPullServiceConfiguration.class})
@ActiveProfiles("tests")
@WebMvcTest(AccountsController.class)
public class AccountsControllerTest extends SwgohApiCommonTests {

  @Autowired private MockMvc mockMvc;

  private static final int ALLYCODE = 123;

  @BeforeEach
  public void setUp() {
    mockGetPlayer(ALLYCODE, getPlayerShortExample());
  }

  @Test
  public void testFetchByAllyCode() throws Exception {
    mockMvc
        .perform(get("/accounts/" + ALLYCODE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("\"name\": \"Pogrommist\"")));
  }
}
