package io.github.ibims1ckoky.lightcontroller.discovery.room.api;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.model.LampUpdateType;
import io.github.ibims1ckoky.lightcontroller.discovery.room.model.RoomArchetype;

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
