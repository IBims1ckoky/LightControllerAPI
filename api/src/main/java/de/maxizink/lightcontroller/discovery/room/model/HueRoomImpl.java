package de.maxizink.lightcontroller.discovery.room.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.ResourceType;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.model.LampUpdateType;
import de.maxizink.lightcontroller.discovery.room.api.HueRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class HueRoomImpl implements HueRoom {

  private final HueBridge hueBridge;
  private final String roomName;
  private final RoomArchetype roomArchetype;
  private final UUID id;

  private final List<UUID> lampIDs = new ArrayList<>();

  public HueRoomImpl(final JsonObject dataObject, final HueBridge hueBridge) {
    this.hueBridge = hueBridge;

    JsonObject metaDataObject = dataObject.getAsJsonObject("metadata");
    this.roomName = metaDataObject.get("name").getAsString();
    this.roomArchetype = RoomArchetype.getRoomArchetype(metaDataObject.get("archetype").getAsString());

    this.id = UUID.fromString(dataObject.get("id").getAsString());

    JsonArray jsonArray = dataObject.getAsJsonArray("services");
    for (JsonElement jsonElement : jsonArray) {
      JsonObject object = jsonElement.getAsJsonObject();
      ResourceType resourceType = ResourceType.getResourceType(object.get("rtype").getAsString());
      if (resourceType.equals(ResourceType.LIGHT)) {
        this.lampIDs.add(UUID.fromString(object.get("rid").getAsString()));
      }
    }
  }

  @Override
  public void turnOn() {
    changeRoomStates(true);
  }

  @Override
  public void turnOff() {
    changeRoomStates(false);
  }

  @Override
  public <T> void updateRoomLamps(final LampUpdateType<T> lampUpdateType, final T... value) {
    getLamps().forEach(hueLamp -> hueLamp.updateSync(lampUpdateType, value));
  }

  @Override
  public String getName() {
    return this.roomName;
  }

  @Override
  public UUID getId() {
    return this.id;
  }

  @Override
  public RoomArchetype getRoomArchetype() {
    return this.roomArchetype;
  }

  @Override
  public HueBridge getHueBridge() {
    return this.hueBridge;
  }

  /**
   * This class does not cache the HueLamps because the data of the lamps can be old
   * when caching the lamps.
   *
   * @return a list of {@link HueLamp} from the {@link HueRoom}
   */
  @Override
  public List<HueLamp> getLamps() {
    return this.lampIDs.stream()
            .map(this.hueBridge::getLampById)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
  }

  private void changeRoomStates(final boolean enabled) {
    for (HueLamp lamp : getLamps()) {
      lamp.updateSync(LampUpdateType.ENABLE, enabled);
    }
  }

}
