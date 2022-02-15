package io.github.ibims1ckoky.lightcontroller;

import io.github.ibims1ckoky.lightcontroller.discovery.RoomDiscoveryExample;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import io.github.ibims1ckoky.lightcontroller.injection.ServiceAccessor;
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
    //new BridgeDiscoveryExample();
    //new LampDiscoveryExample();
    new RoomDiscoveryExample();

    //new LightControllerUsageExample();
  }
}
