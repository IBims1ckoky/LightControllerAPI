package de.maxizink.lightcontroller.service;

import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeScanner;
import de.maxizink.lightcontroller.discovery.bridge.service.AsyncBridgeDiscoveryService;
import de.maxizink.lightcontroller.discovery.bridge.service.BridgeDiscoveryService;
import de.maxizink.lightcontroller.discovery.bridge.service.BridgeScannerService;

import java.util.HashMap;
import java.util.Map;

public class ServiceAccessor {

  private static final Map<Class<? extends Service>, Service> SERVICES = new HashMap<>();

  static {
    SERVICES.put(BridgeDiscovery.class, new BridgeDiscoveryService());
    SERVICES.put(AsyncBridgeDiscovery.class, new AsyncBridgeDiscoveryService());
    SERVICES.put(BridgeScanner.class, new BridgeScannerService());
  }

  public static <T extends Service> T accessService(final Class<T> clazz) {
    return (T) SERVICES.get(clazz);
  }

}
