package io.github.ibims1ckoky.lightcontroller.discovery;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.model.LampUpdateType;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoom;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoomDiscovery;
import io.github.ibims1ckoky.lightcontroller.injection.ServiceAccessor;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoomDiscoveryExample {

  public RoomDiscoveryExample() {
    discoverAllRooms();
  }

  private void getHueRooms(final HueBridge hueBridge) {
    HueRoomDiscovery hueRoomDiscovery = ServiceAccessor.accessService(HueRoomDiscovery.class);

    List<HueRoom> rooms = hueBridge.getRooms();
    List<HueRoom> roomsByService = hueRoomDiscovery.discoverAllRooms(hueBridge); //Does exactly the same
  }

  private void updateRooms(final HueBridge hueBridge) {
    HueRoomDiscovery hueRoomDiscovery = ServiceAccessor.accessService(HueRoomDiscovery.class);

    Optional<HueRoom> hueRoomByName = hueBridge.getHueRoomByName("Room");
    hueRoomByName.ifPresent(hueRoom -> {
      hueRoom.turnOn();

      hueRoom.updateRoomLamps(LampUpdateType.COLOR, Color.RED);
      hueRoom.updateRoomLamps(LampUpdateType.BRIGHTNESS, 50);
    });
  }

  private void getHueRoomByName(final HueBridge hueBridge) {
    HueRoomDiscovery hueRoomDiscovery = ServiceAccessor.accessService(HueRoomDiscovery.class);

    Optional<HueRoom> hueRoomByName = hueBridge.getHueRoomByName("Room");
    Optional<HueRoom> hueRoomByNameByService = hueRoomDiscovery.getHueRoomByName(hueBridge, "Room");

    Optional<HueRoom> hueRoomById = hueBridge.getHueRoomById(UUID.randomUUID());
    Optional<HueRoom> hueRoomByIdByService = hueRoomDiscovery.getHueRoomById(hueBridge, UUID.randomUUID());
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
