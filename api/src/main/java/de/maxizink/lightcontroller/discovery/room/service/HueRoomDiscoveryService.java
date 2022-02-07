package de.maxizink.lightcontroller.discovery.room.service;

import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.room.api.HueRoom;
import de.maxizink.lightcontroller.discovery.room.api.HueRoomDiscovery;
import de.maxizink.lightcontroller.utils.HueResponseValidator;
import de.maxizink.lightcontroller.utils.JsonUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.List;

import static de.maxizink.lightcontroller.utils.HttpUtils.createRequest;
import static de.maxizink.lightcontroller.utils.HttpUtils.executeJson;

public class HueRoomDiscoveryService implements HueRoomDiscovery {

  @Override
  public List<HueRoom> discoverAllRooms(final HueBridge hueBridge) {
    HttpUriRequest httpRequest = createRequest(URLFormatter.getAllRooms(hueBridge.getHueBridgeCredentials().getIpAddress()));
    JsonObject dataObject = JsonUtils.fromJson(executeJson(httpRequest));
    if (HueResponseValidator.hasErrors(dataObject)) {

    }

    return List.of();
  }
}
