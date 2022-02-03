package de.maxizink.lightcontroller.discovery.bridge.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BridgeIpDiscoveryResponse {

  private String id;
  private String internalipaddress;
  private int port;

  public int getPort() {
    return port;
  }

  public String getAddress() {
    return internalipaddress;
  }
}
