package io.github.ibims1ckoky.lightcontroller;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.model.LampUpdateType;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoom;
import io.github.ibims1ckoky.lightcontroller.injection.ServiceAccessor;

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

    List<HueRoom> allRooms = hueBridge.getRooms();

    hueBridge.getHueRoomByName("Max Zimmer").ifPresent(hueRoom -> {

    });
  }

  private HueBridgeCredentials getCredentials(final String bridgeIp) {
    BridgeCredentialsDiscovery bridgeCredentialsDiscovery = ServiceAccessor.accessService(BridgeCredentialsDiscovery.class);






    HueBridgeCredentials hueBridgeCredentials = new HueBridgeCredentials(
            "", // This is for testing hard coded
            "", // This for testing hard coded
            bridgeIp
    );

    HueBridgeCredentialsResponse other = bridgeCredentialsDiscovery.generateHueBridgeCredentials(bridgeIp);
    if (other.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
      return other.getHueBridgeCredentials();
    }
    return hueBridgeCredentials;
  }

}
