package io.github.ibims1ckoky.lightcontroller.discovery.bridge.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HueBridgeCredentials {

  private final String userName;
  private final String clientKey;

  private String ipAddress;

}
