package com.amurgin.swgoh.tracker.accounts.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.amurgin.swgoh.tracker.accounts.configuration.AccountsControllerTestConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = AccountsControllerTestConfiguration.class)
@WebMvcTest(AccountsController.class)
public class AccountsControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void testRegister() throws Exception {
    var body =
        """
        {
            "uuid": "tests-123",
            "accountInfo": {
                "allyCode": 123456,
                "playerSources": [
                    {
                        "id": "id123",
                        "type": "TELEGRAM"
                    }
                ]
            }
        }
        """;
    mockMvc
        .perform(
            post("/accounts/register")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpectAll(
            jsonPath("$.uuid").value("tests-123"),
            jsonPath("$.accountId").isNotEmpty(),
            jsonPath("$.accountName").isNotEmpty(),
            jsonPath("$.accountGalacticPower").isNotEmpty());
  }

  @Test
  public void testRegister_invalidRequest() throws Exception {
    var body =
        """
        {
            "uuid": "tests-123",
            "accountInfo": {
                "allyCode": 123456
            }
        }
        """;
    mockMvc
        .perform(
            post("/accounts/register")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .string(
                    "Unable to register the account : Non-empty playerSources field should be"
                        + " specified"));
  }
}
