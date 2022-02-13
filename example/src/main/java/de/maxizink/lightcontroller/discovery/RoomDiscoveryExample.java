package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.model.LampUpdateType;
import de.maxizink.lightcontroller.discovery.room.api.HueRoom;
import de.maxizink.lightcontroller.discovery.room.api.HueRoomDiscovery;
import de.maxizink.lightcontroller.injection.ServiceAccessor;

import java.awt.*;
import java.util.List;

public class RoomDiscoveryExample {

  public RoomDiscoveryExample() {
    discoverAllRooms();
  }

  private void discoverAllRooms() {
    HueBridgeDiscovery hueBridgeDiscovery = ServiceAccessor.accessService(HueBridgeDiscovery.class);
    HueRoomDiscovery hueRoomDiscovery = ServiceAccessor.accessService(HueRoomDiscovery.class);

    HueBridgeCredentials hueBridgeCredentials = new HueBridgeCredentials(
            "", // This is for testing hard coded
            "", // This for testing hard coded
            ""
    );

    HueBridge hueBridge = hueBridgeDiscovery.discoverHueBridge(hueBridgeCredentials);
    List<HueRoom> hueRooms = hueRoomDiscovery.discoverAllRooms(hueBridge); //get all rooms by service

    //get room by
    hueRoomDiscovery.getHueRoomByName(hueBridge, "Max Zimmer").ifPresent(hueRoom -> {
      hueRoom.getName();
      hueRoom.getRoomArchetype();
      hueRoom.turnOff();

      hueRoom.updateRoomLamps(LampUpdateType.COLOR, Color.GREEN);
      hueRoom.updateRoomLamps(LampUpdateType.BRIGHTNESS, 10);

      for (HueLamp lamp : hueRoom.getLamps()) {
        lamp.getName();
        lamp.getId();
      }
    });
  }

}
