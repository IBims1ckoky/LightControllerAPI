package de.maxizink.lightcontroller.discovery.room.api;

import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.model.LampUpdateType;
import de.maxizink.lightcontroller.discovery.room.model.RoomArchetype;

import java.util.List;
import java.util.UUID;

public interface HueRoom {

  <T> void updateRoomLamps(final LampUpdateType<T> lampUpdateType, final T... value);

  void turnOn();

  void turnOff();

  String getName();

  UUID getId();

  RoomArchetype getRoomArchetype();

  HueBridge getHueBridge();

  List<HueLamp> getLamps();

}
