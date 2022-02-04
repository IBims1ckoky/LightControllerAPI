package de.maxizink.lightcontroller.service;

import de.maxizink.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.service.BridgeCredentialsDiscoveryService;
import de.maxizink.lightcontroller.discovery.bridge.service.BridgeIpDiscoveryService;
import de.maxizink.lightcontroller.discovery.bridge.service.HueBridgeDiscoveryService;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import de.maxizink.lightcontroller.discovery.lamp.service.HueLampDiscoveryService;
import de.maxizink.lightcontroller.utils.TrustEverythingUtil;

import java.util.HashMap;
import java.util.Map;

public class ServiceAccessor {

  private static final Map<Class<? extends Service>, Service> SERVICES = new HashMap<>();

  static {
    TrustEverythingUtil.trustAllSslConnectionsByDisablingCertificateVerification();

    SERVICES.put(BridgeIpDiscovery.class, new BridgeIpDiscoveryService());
    SERVICES.put(BridgeCredentialsDiscovery.class, new BridgeCredentialsDiscoveryService());
    SERVICES.put(HueBridgeDiscovery.class, new HueBridgeDiscoveryService());
    SERVICES.put(HueLampDiscovery.class, new HueLampDiscoveryService());
  }

  public static <T extends Service> T accessService(final Class<T> clazz) {
    return (T) SERVICES.get(clazz);
  }

}
