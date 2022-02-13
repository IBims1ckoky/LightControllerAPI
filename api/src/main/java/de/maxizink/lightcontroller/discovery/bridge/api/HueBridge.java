package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.models.BridgeInfo;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.room.api.HueRoom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HueBridge {

  //Lamps
  List<HueLamp> getLamps();

  Optional<HueLamp> getLampById(final UUID uniqueId);

  Optional<HueLamp> getLampByName(final String name);

  //Rooms
  List<HueRoom> getRooms();

  Optional<HueRoom> getHueRoomById(final UUID uniqueId);

  Optional<HueRoom> getHueRoomByName(final String name);

  //Information
  BridgeInfo getBridgeInfo();

  HueBridgeCredentials getHueBridgeCredentials();

}
