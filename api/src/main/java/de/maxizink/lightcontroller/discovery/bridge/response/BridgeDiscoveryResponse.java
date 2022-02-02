package de.maxizink.lightcontroller.discovery.bridge.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BridgeDiscoveryResponse {

  private String id;
  private String internalipaddress;
  private int port;

}
