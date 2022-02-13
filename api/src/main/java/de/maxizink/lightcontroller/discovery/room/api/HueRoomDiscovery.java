package de.maxizink.lightcontroller.discovery.room.api;

import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.injection.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HueRoomDiscovery extends Service {

  Optional<HueRoom> getHueRoomById(final HueBridge hueBridge, final UUID uniqueId);

  Optional<HueRoom> getHueRoomByName(final HueBridge hueBridge, final String name);

  List<HueRoom> discoverAllRooms(final HueBridge hueBridge);

}
