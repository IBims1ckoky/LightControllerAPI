package de.maxizink.lightcontroller.discovery.room.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.room.api.HueRoom;
import de.maxizink.lightcontroller.discovery.room.api.HueRoomDiscovery;
import de.maxizink.lightcontroller.discovery.room.exception.HueRoomDiscoveryException;
import de.maxizink.lightcontroller.discovery.room.model.HueRoomImpl;
import de.maxizink.lightcontroller.utils.HueResponseValidator;
import de.maxizink.lightcontroller.utils.JsonUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static de.maxizink.lightcontroller.utils.HttpUtils.*;

public class HueRoomDiscoveryService implements HueRoomDiscovery {

  @Override
  public Optional<HueRoom> getHueRoomById(final HueBridge hueBridge, final UUID uniqueId) {
    return discoverAllRooms(hueBridge).stream()
            .filter(hueRoom -> hueRoom.getId().equals(uniqueId))
            .findFirst();
  }

  @Override
  public Optional<HueRoom> getHueRoomByName(final HueBridge hueBridge, final String name) {
    return discoverAllRooms(hueBridge).stream()
            .filter(hueRoom -> hueRoom.getName().equals(name))
            .findFirst();
  }

  @Override
  public List<HueRoom> discoverAllRooms(final HueBridge hueBridge) {
    HttpUriRequest httpRequest = createRequest(URLFormatter.getAllRooms(hueBridge.getHueBridgeCredentials().getIpAddress()),
            createCredentialHeader(hueBridge.getHueBridgeCredentials()));

    String json = executeJson(httpRequest);

    JsonObject dataObject = JsonUtils.fromJson(executeJson(httpRequest));
    if (HueResponseValidator.hasErrors(dataObject)) {
      throw new HueRoomDiscoveryException("Error while discover Rooms");
    }

    List<HueRoom> hueRooms = new ArrayList<>();
    JsonArray jsonLampArray = dataObject.getAsJsonArray("data");
    for (JsonElement jsonElement : jsonLampArray) {
      HueRoom hueRoom = new HueRoomImpl(jsonElement.getAsJsonObject(), hueBridge);
      hueRooms.add(hueRoom);
    }

    return hueRooms;
  }
}
