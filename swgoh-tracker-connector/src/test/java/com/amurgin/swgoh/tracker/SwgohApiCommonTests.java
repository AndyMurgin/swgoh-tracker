package com.amurgin.swgoh.tracker;

import help.swgoh.api.SwgohAPI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

public class SwgohApiCommonTests {

  @Autowired protected SwgohAPI swgohApiConnector;

  protected String getPlayerShortExample() {
    try {
      return new String(
          Objects.requireNonNull(getClass().getResourceAsStream("/example-player-short.json"))
              .readAllBytes(),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("Problem with reading example-player.json: " + e.getMessage());
    }
  }

  protected void mockGetPlayer(Integer allyCode, String expected) {
    Mockito.when(swgohApiConnector.getPlayer(allyCode))
        .thenReturn(CompletableFuture.completedFuture(expected));
  }
}
