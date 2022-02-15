package io.github.ibims1ckoky.lightcontroller.discovery.room.api;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.injection.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HueRoomDiscovery extends Service {

  Optional<HueRoom> getHueRoomById(final HueBridge hueBridge, final UUID uniqueId);

  Optional<HueRoom> getHueRoomByName(final HueBridge hueBridge, final String name);

  List<HueRoom> discoverAllRooms(final HueBridge hueBridge);

}
