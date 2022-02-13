package de.maxizink.lightcontroller.discovery.lamp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import de.maxizink.lightcontroller.discovery.lamp.exception.HueLampDiscoveryException;
import de.maxizink.lightcontroller.discovery.lamp.model.HueLampImpl;
import de.maxizink.lightcontroller.utils.HttpUtils;
import de.maxizink.lightcontroller.utils.HueResponseValidator;
import de.maxizink.lightcontroller.utils.JsonUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static de.maxizink.lightcontroller.utils.HttpUtils.*;

public class HueLampDiscoveryService implements HueLampDiscovery {

  @Override
  public List<HueLamp> discoverAllLamps(final HueBridge hueBridge) {
    HttpClient httpClient = createClient();
    HttpUriRequest httpRequest = createRequest(URLFormatter.getAllLamps(hueBridge.getHueBridgeCredentials().getIpAddress()),
            HttpUtils.createCredentialHeader(hueBridge.getHueBridgeCredentials()));

    String json = executeJson(httpClient, httpRequest);
    JsonObject jsonObject = JsonUtils.fromJson(json);
    if (HueResponseValidator.hasErrors(jsonObject)) {
      throw new HueLampDiscoveryException("Error while fetching HueLamps");
    }

    List<HueLamp> hueLamps = new ArrayList<>();
    JsonArray jsonLampArray = jsonObject.getAsJsonArray("data");
    for (JsonElement jsonElement : jsonLampArray) {
      HueLamp hueLamp = new HueLampImpl(jsonElement.getAsJsonObject(), hueBridge);
      hueLamps.add(hueLamp);
    }

    return hueLamps;
  }

  @Override
  public CompletableFuture<List<HueLamp>> discoverAllLampsAsync(final HueBridge hueBridge) {
    return CompletableFuture.supplyAsync(() -> discoverAllLamps(hueBridge));
  }

  @Override
  public JsonObject getLampObject(final HueLamp hueLamp) {
    HueBridge hueBridge = hueLamp.getHueBridge();
    HttpClient httpClient = createClient();
    HttpUriRequest httpRequest = createRequest(URLFormatter.getAllLamps(hueBridge.getHueBridgeCredentials().getIpAddress()),
            HttpUtils.createCredentialHeader(hueBridge.getHueBridgeCredentials()));

    String json = executeJson(httpClient, httpRequest);
    JsonObject jsonObject = JsonUtils.fromJson(json);
    if (HueResponseValidator.hasErrors(jsonObject)) {
      throw new HueLampDiscoveryException("Error while fetching HueLamps");
    }

    JsonArray jsonLampArray = jsonObject.getAsJsonArray("data");
    for (JsonElement jsonElement : jsonLampArray) {
      HueLamp lampImpl = new HueLampImpl(jsonElement.getAsJsonObject(), hueBridge);
      if (lampImpl.getId().equals(hueLamp.getId())) {
        return jsonElement.getAsJsonObject();
      }
    }
    throw new IllegalArgumentException("Unknown Lamp");
  }
}
