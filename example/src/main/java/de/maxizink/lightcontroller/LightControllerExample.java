package de.maxizink.lightcontroller;

import de.maxizink.lightcontroller.discovery.BridgeDiscoveryExample;
import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
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
    AsyncBridgeDiscovery asyncBridgeDiscovery = ServiceAccessor.accessService(AsyncBridgeDiscovery.class);

    //Run all Examples
    new BridgeDiscoveryExample();

    //This Example where not executed by default because it scans one minute
    //new BridgeScannerExample();
  }

}
