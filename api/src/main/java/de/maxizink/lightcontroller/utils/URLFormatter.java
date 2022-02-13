package de.maxizink.lightcontroller.utils;

import lombok.SneakyThrows;

import java.net.URI;
import java.util.UUID;

public class URLFormatter {

  @SneakyThrows
  public static URI getBridgeAPIKey(final String bridgeIp) {
    return new URI("https://" + bridgeIp + "/api");
  }

  @SneakyThrows
  public static URI getBridgeConfig(final String bridgeIp) {
    return new URI("https://" + bridgeIp + "/api/config");
  }

  @SneakyThrows
  public static URI getAllLamps(final String bridgeIp) {
    return new URI("https://" + bridgeIp + "/clip/v2/resource/light");
  }

  @SneakyThrows
  public static URI getLamp(final String bridgeIp, final UUID id) {
    return new URI("https://" + bridgeIp + "/clip/v2/resource/light/" + id.toString());
  }

  @SneakyThrows
  public static URI getAllRooms(final String bridgeIp) {
    return new URI("https://" + bridgeIp + "/clip/v2/resource/room");
  }
}
