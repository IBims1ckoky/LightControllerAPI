package io.github.ibims1ckoky.lightcontroller.injection;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.service.BridgeCredentialsDiscoveryService;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.service.BridgeIpDiscoveryService;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.service.HueBridgeDiscoveryService;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.service.HueLampDiscoveryService;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoomDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.room.service.HueRoomDiscoveryService;
import io.github.ibims1ckoky.lightcontroller.utils.TrustEverythingUtil;

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
    SERVICES.put(HueRoomDiscovery.class, new HueRoomDiscoveryService());
  }

  public static <T extends Service> T accessService(final Class<T> clazz) {
    return (T) SERVICES.get(clazz);
  }

}
