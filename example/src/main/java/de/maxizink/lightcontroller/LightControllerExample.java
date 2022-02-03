package de.maxizink.lightcontroller;

import de.maxizink.lightcontroller.discovery.BridgeDiscoveryExample;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.service.ServiceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LightControllerExample {

  @SneakyThrows
  public static void main(final String[] args) {
    //With that you can load all Services
    BridgeIpDiscovery bridgeIpDiscovery = ServiceAccessor.accessService(BridgeIpDiscovery.class);
    BridgeCredentialsDiscovery bridgeCredentialsDiscovery = ServiceAccessor.accessService(BridgeCredentialsDiscovery.class);
    HueBridgeDiscovery hueBridgeDiscovery = ServiceAccessor.accessService(HueBridgeDiscovery.class);

    //Run all Examples
    new BridgeDiscoveryExample();
  }
}
