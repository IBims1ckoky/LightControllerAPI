package de.maxizink.lightcontroller.discovery.room.api;

import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.injection.Service;

import java.util.List;

public interface HueRoomDiscovery extends Service {

  List<HueRoom> discoverAllRooms(final HueBridge hueBridge);

}
