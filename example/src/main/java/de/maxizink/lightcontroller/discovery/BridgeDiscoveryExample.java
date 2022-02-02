package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BridgeDiscoveryExample {

  @SneakyThrows
  public BridgeDiscoveryExample(final BridgeDiscovery bridgeDiscovery, final AsyncBridgeDiscovery asyncBridgeDiscovery) {
    log.info("Sync - SingleBridgeIP: " + bridgeDiscovery.getBridgeIp());
    for (String allBridgeIp : bridgeDiscovery.getAllBridgeIps()) {
      log.info("Sync - AllBridgeIP: " + allBridgeIp);
    }

    log.info("Async - BridgeIP: " + asyncBridgeDiscovery.getBridgeIp().get());
    for (String allBridgeIps : asyncBridgeDiscovery.getAllBridgeIps().get()) {
      log.info("Aync - AllBridgeIP: " + allBridgeIps);
    }
  }
}
