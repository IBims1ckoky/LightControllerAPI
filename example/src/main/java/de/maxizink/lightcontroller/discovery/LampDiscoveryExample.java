package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import de.maxizink.lightcontroller.discovery.lamp.model.HueColor;
import de.maxizink.lightcontroller.discovery.lamp.model.LampUpdateType;
import de.maxizink.lightcontroller.service.ServiceAccessor;

import java.util.List;
import java.util.Optional;

public class LampDiscoveryExample {

  public LampDiscoveryExample() {
    doLightInfo();
  }

  private void doLightInfo() {
    HueBridgeDiscovery hueBridgeDiscovery = ServiceAccessor.accessService(HueBridgeDiscovery.class);
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);

    HueBridgeCredentials hueBridgeCredentials = new HueBridgeCredentials(
            "ixFWtIB--hLGkv5m7t1Xksstvpt1mmzxTXQbiWVL",
            "87D82277C67FE7FD1059809616B640BF",
            "192.168.2.124"
    );

    HueBridge hueBridge = hueBridgeDiscovery.discoverHueBridge(hueBridgeCredentials);
    //syncLamps(hueBridge);
    //asyncLamps(hueBridge);
    //updateLamp(hueBridge);
  }

  private void updateLamp(final HueBridge hueBridge) {
    HueLamp hueLamp = hueBridge.getLampByName("Max Bett").get();
    hueLamp.updateSync(LampUpdateType.COLOR, HueColor.ORANGE);
    hueLamp.updateSync(LampUpdateType.BRIGHTNESS, 100);
    hueLamp.updateAsync(LampUpdateType.ENABLE, true).join();
    hueLamp.updateSync(LampUpdateType.COLOR_TEMPERATURE, 500);
  }

  private void syncLamps(final HueBridge hueBridge) {
    Optional<HueLamp> optionalHueLamp = hueBridge.getLampByName("My Lamp");
    List<HueLamp> hueLamps = hueBridge.getLamps();

    hueLamps.forEach(hueLamp -> {
      //Sync Lamps
      hueLamp.getId();
      hueLamp.getName();
    });
  }

  private void asyncLamps(final HueBridge hueBridge) {
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);
    hueLampDiscovery.discoverAllLampsAsync(hueBridge).thenAcceptAsync(hueLamps -> {
      hueLamps.forEach(hueLamp -> {
        //Async Lamps
        hueLamp.getId();
        hueLamp.getName();
      });
    });
  }


}
