package de.maxizink.lightcontroller.utils;

import lombok.SneakyThrows;

import java.net.URI;

public class HueBridgeURLUtils {

  @SneakyThrows
  public static URI getBridgeAPIKey(final String bridgeIp) {
    return new URI("http://" + bridgeIp + "/api");
  }

}
