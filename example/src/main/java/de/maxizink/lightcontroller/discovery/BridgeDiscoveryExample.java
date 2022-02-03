package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.service.ServiceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BridgeDiscoveryExample {


  public BridgeDiscoveryExample() {
    discoverBridgeIP(); //This shows all Methods to get the BridgeIP
    createApiKey(); //This should create an API Key
  }

  /**
   * This shows the Methods from the {@link BridgeDiscovery} and the {@link AsyncBridgeDiscovery} to
   * get the BridgeIP(s).
   */
  @SneakyThrows
  private void discoverBridgeIP() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    AsyncBridgeDiscovery asyncBridgeDiscovery = ServiceAccessor.accessService(AsyncBridgeDiscovery.class);

    String bridgeIp = bridgeDiscovery.getBridgeIp(); //Sync
    List<String> bridgeIps = bridgeDiscovery.getAllBridgeIps(); //Sync

    String asyncBridgeIp = asyncBridgeDiscovery.getBridgeIp().get(); //Async
    List<String> asyncBridgeIps = asyncBridgeDiscovery.getAllBridgeIps().get(); //Async
  }

  @SneakyThrows
  private void createApiKey() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    String bridgeIp = bridgeDiscovery.getBridgeIp();
    String apiKey = bridgeDiscovery.getAPIKey(bridgeIp);
  }

}
