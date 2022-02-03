package de.maxizink.lightcontroller.utils;

import lombok.SneakyThrows;

import java.net.URI;

public class URLFormatter {

  @SneakyThrows
  public static URI getBridgeAPIKey(final String bridgeIp) {
    return new URI("https://" + bridgeIp + "/api");
  }

  @SneakyThrows
  public static URI getBridgeConfig(final String bridgeIp) {
    return new URI("https://" + bridgeIp + "/api/config");
  }


}
