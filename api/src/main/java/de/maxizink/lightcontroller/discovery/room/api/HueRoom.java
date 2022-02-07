package de.maxizink.lightcontroller.discovery.room.api;

import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.room.model.RoomArchetype;

import java.util.List;

public interface HueRoom {

  String getName();

  RoomArchetype getRoomArchetype();

  HueBridge getHueBridge();

  List<HueLamp> getLamps();

}
