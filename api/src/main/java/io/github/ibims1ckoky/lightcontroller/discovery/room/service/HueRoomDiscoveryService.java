package io.github.ibims1ckoky.lightcontroller.discovery.room.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoom;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoomDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.room.exception.HueRoomDiscoveryException;
import io.github.ibims1ckoky.lightcontroller.discovery.room.model.HueRoomImpl;
import io.github.ibims1ckoky.lightcontroller.utils.HueResponseValidator;
import io.github.ibims1ckoky.lightcontroller.utils.JsonUtils;
import io.github.ibims1ckoky.lightcontroller.utils.URLFormatter;
import io.github.ibims1ckoky.lightcontroller.utils.HttpUtils;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    HttpUriRequest httpRequest = HttpUtils.createRequest(URLFormatter.getAllRooms(hueBridge.getHueBridgeCredentials().getIpAddress()),
            HttpUtils.createCredentialHeader(hueBridge.getHueBridgeCredentials()));

    String json = HttpUtils.executeJson(httpRequest);

    JsonObject dataObject = JsonUtils.fromJson(HttpUtils.executeJson(httpRequest));
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
