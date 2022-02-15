package io.github.ibims1ckoky.lightcontroller.discovery.bridge.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.exception.HueBridgeInformationDiscoveryException;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoom;
import io.github.ibims1ckoky.lightcontroller.discovery.room.api.HueRoomDiscovery;
import io.github.ibims1ckoky.lightcontroller.injection.ServiceAccessor;
import io.github.ibims1ckoky.lightcontroller.mapper.CustomObjectMapper;
import io.github.ibims1ckoky.lightcontroller.utils.HttpUtils;
import io.github.ibims1ckoky.lightcontroller.utils.URLFormatter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
public class HueBridgeImpl implements HueBridge {

  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  private final String dataUri;
  private final HueBridgeCredentials hueBridgeCredentials;
  private final BridgeInfo bridgeInfo;

  public HueBridgeImpl(final HueBridgeCredentials hueBridgeCredentials) {
    this.hueBridgeCredentials = hueBridgeCredentials;
    this.dataUri = "https://" + hueBridgeCredentials.getIpAddress() + "/clip/v2/resource/bridge";
    this.bridgeInfo = fetchBridgeInfo();
  }

  @SneakyThrows
  private BridgeInfo fetchBridgeInfo() {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpUriRequest = HttpUtils.createRequest(URLFormatter.getBridgeConfig(hueBridgeCredentials.getIpAddress()));
    String json = HttpUtils.executeJson(httpClient, httpUriRequest);
    return OBJECT_MAPPER.readValue(json, BridgeInfo.class);
  }

  @SneakyThrows
  private JsonArray getDataArray() {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpUriRequest = HttpUtils.createRequest(URI.create(dataUri),
            new BasicHeader(HttpHeaders.HOST, this.hueBridgeCredentials.getIpAddress()),
            HttpUtils.createCredentialHeader(hueBridgeCredentials)
    );

    String json = HttpUtils.executeJson(httpClient, httpUriRequest);
    return checkErrors(JsonParser.parseString(json).getAsJsonObject());
  }

  private JsonArray checkErrors(final JsonObject jsonObject) {
    JsonArray errorElement = jsonObject.get("errors").getAsJsonArray();
    if (errorElement.size() >= 1) {
      throw new HueBridgeInformationDiscoveryException("An error occurred while fetch data from the Bridge");
    }

    return jsonObject.get("data").getAsJsonArray();
  }


  @Override
  public List<HueLamp> getLamps() {
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);
    return hueLampDiscovery.discoverAllLamps(this);
  }

  @Override
  public Optional<HueLamp> getLampById(final UUID uniqueId) {
    return getLamps().stream()
            .filter(hueLamp -> hueLamp.getId().equals(uniqueId))
            .findFirst();
  }

  @Override
  public Optional<HueLamp> getLampByName(final String name) {
    return getLamps().stream()
            .filter(hueLamp -> hueLamp.getName().equalsIgnoreCase(name))
            .findFirst();
  }

  @Override
  public List<HueRoom> getRooms() {
    HueRoomDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueRoomDiscovery.class);
    return hueLampDiscovery.discoverAllRooms(this);
  }

  @Override
  public Optional<HueRoom> getHueRoomById(final UUID uniqueId) {
    return getRooms().stream()
            .filter(hueRoom -> hueRoom.getId().equals(uniqueId))
            .findFirst();
  }

  @Override
  public Optional<HueRoom> getHueRoomByName(final String name) {
    return getRooms().stream()
            .filter(hueRoom -> hueRoom.getName().equals(name))
            .findFirst();
  }
}
