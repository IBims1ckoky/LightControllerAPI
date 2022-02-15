package io.github.ibims1ckoky.lightcontroller.discovery.bridge.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BridgeIpDiscoveryResponse {

  private String id;
  private String internalipaddress;
  private int port;

  public String getAddress() {
    return internalipaddress;
  }
}
