package de.maxizink.lightcontroller;

import de.maxizink.lightcontroller.discovery.BridgeDiscoveryExample;
import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.service.AsyncBridgeDiscoveryService;
import de.maxizink.lightcontroller.discovery.bridge.service.BridgeDiscoveryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LightControllerExample {

  @SneakyThrows
  public static void main(final String[] args) {
    BridgeDiscovery bridgeDiscovery = new BridgeDiscoveryService();
    AsyncBridgeDiscovery asyncBridgeDiscovery = new AsyncBridgeDiscoveryService();

    BridgeDiscoveryExample bridgeDiscoveryExample = new BridgeDiscoveryExample(bridgeDiscovery, asyncBridgeDiscovery);
  }

}
