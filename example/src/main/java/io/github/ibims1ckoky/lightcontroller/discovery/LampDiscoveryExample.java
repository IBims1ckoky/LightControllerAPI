package io.github.ibims1ckoky.lightcontroller.discovery;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.model.LampUpdateType;
import io.github.ibims1ckoky.lightcontroller.injection.ServiceAccessor;
import lombok.SneakyThrows;

import java.awt.*;
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
            "name",
            "key",
            ""
    );

    HueBridge hueBridge = hueBridgeDiscovery.discoverHueBridge(hueBridgeCredentials);
    //syncLamps(hueBridge);
    //asyncLamps(hueBridge);
    //updateLamp(hueBridge);
    byName(hueBridge);
  }

  private void byName(HueBridge hueBridge) {
    Optional<HueLamp> optionalHueLamp = hueBridge.getLampByName("My Lamp");
    optionalHueLamp.ifPresent(hueLamp -> {
      hueLamp.getId();
      hueLamp.getName();
    });
  }

  private void updateLamp(final HueBridge hueBridge) {
    HueLamp hueLamp = hueBridge.getLampByName("Max Bett").get();
    hueLamp.updateSync(LampUpdateType.COLOR, Color.ORANGE);
    hueLamp.updateSync(LampUpdateType.BRIGHTNESS, 100);
    hueLamp.updateSync(LampUpdateType.COLOR_TEMPERATURE, 500);
    hueLamp.updateAsync(LampUpdateType.ENABLE, true).join();
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

  @SneakyThrows
  private void asyncLamps(final HueBridge hueBridge) {
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);
    List<HueLamp> hueLamps = hueLampDiscovery.discoverAllLampsAsync(hueBridge).get();

    hueLamps.forEach(hueLamp -> {
      //Sync Lamps
      hueLamp.getId();
      hueLamp.getName();
    });
  }


}
