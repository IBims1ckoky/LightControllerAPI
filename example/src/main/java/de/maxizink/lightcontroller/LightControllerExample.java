package de.maxizink.lightcontroller;

import de.maxizink.lightcontroller.discovery.BridgeDiscoveryExample;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.service.ServiceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LightControllerExample {

  @SneakyThrows
  public static void main(final String[] args) {
    //With that you can load all Services
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);

    //Run all Examples
    new BridgeDiscoveryExample();
  }

}
