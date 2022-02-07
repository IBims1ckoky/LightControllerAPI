package de.maxizink.lightcontroller;

import de.maxizink.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import de.maxizink.lightcontroller.discovery.lamp.model.LampUpdateType;
import de.maxizink.lightcontroller.service.ServiceAccessor;

import java.util.List;

public class LightControllerUsageExample {

  public LightControllerUsageExample() {
    BridgeIpDiscovery bridgeIpDiscovery = ServiceAccessor.accessService(BridgeIpDiscovery.class);
    BridgeCredentialsDiscovery bridgeCredentialsDiscovery = ServiceAccessor.accessService(BridgeCredentialsDiscovery.class);
    HueBridgeDiscovery hueBridgeDiscovery = ServiceAccessor.accessService(HueBridgeDiscovery.class);
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);


    String bridgeIp = bridgeIpDiscovery.discoverBridgeIP();
    HueBridgeCredentials hueBridgeCredentials = getCredentials(bridgeIp);
    HueBridge hueBridge = hueBridgeDiscovery.discoverHueBridge(hueBridgeCredentials);

    List<HueLamp> allHueLampsFromBridge = hueBridge.getLamps();
    //or
    List<HueLamp> allHueLampsFromService = hueLampDiscovery.discoverAllLamps(hueBridge);

    hueBridge.getLampByName("Max Bett").ifPresent(hueLamp -> {
      hueLamp.updateSync(LampUpdateType.ENABLE, true); //Set Enabled to true
      hueLamp.updateSync(LampUpdateType.BRIGHTNESS, 50); //Set the Brightness to 50%
    });
  }

  private HueBridgeCredentials getCredentials(final String bridgeIp) {
    BridgeCredentialsDiscovery bridgeCredentialsDiscovery = ServiceAccessor.accessService(BridgeCredentialsDiscovery.class);

    HueBridgeCredentials hueBridgeCredentials = new HueBridgeCredentials(
            "userName", // This is for testing hard coded
            "clientKey", // This for testing hard coded
            bridgeIp
    );

    HueBridgeCredentialsResponse other = bridgeCredentialsDiscovery.generateHueBridgeCredentials(bridgeIp);
    if (other.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
      return other.getHueBridgeCredentials();
    }
    return hueBridgeCredentials;
  }

}
