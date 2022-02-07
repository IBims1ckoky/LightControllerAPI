package de.maxizink.lightcontroller.discovery.room.model;

import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.room.api.HueRoom;

import java.util.List;

public class HueRoomImpl implements HueRoom {

  private final HueBridge hueBridge;
  private final String roomName;
  private final RoomArchetype roomArchetype;

  public HueRoomImpl(final JsonObject dataObject, final HueBridge hueBridge) {
    this.hueBridge = hueBridge;

    JsonObject metaDataObject = dataObject.getAsJsonObject("metadata");
    this.roomName = metaDataObject.get("name").getAsString();
    this.roomArchetype = RoomArchetype.valueOf(metaDataObject.get("archetype").getAsString());
  }

  @Override
  public String getName() {
    return this.roomName;
  }

  @Override
  public RoomArchetype getRoomArchetype() {
    return this.roomArchetype;
  }

  @Override
  public HueBridge getHueBridge() {
    return this.hueBridge;
  }

  @Override
  public List<HueLamp> getLamps() {
    return null;
  }
}
