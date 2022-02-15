package io.github.ibims1ckoky.lightcontroller.discovery.lamp.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.exception.HueLampDiscoveryException;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.model.HueLampImpl;
import io.github.ibims1ckoky.lightcontroller.utils.HttpUtils;
import io.github.ibims1ckoky.lightcontroller.utils.HueResponseValidator;
import io.github.ibims1ckoky.lightcontroller.utils.JsonUtils;
import io.github.ibims1ckoky.lightcontroller.utils.URLFormatter;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class HueLampDiscoveryService implements HueLampDiscovery {

  @Override
  public List<HueLamp> discoverAllLamps(final HueBridge hueBridge) {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpRequest = HttpUtils.createRequest(URLFormatter.getAllLamps(hueBridge.getHueBridgeCredentials().getIpAddress()),
            HttpUtils.createCredentialHeader(hueBridge.getHueBridgeCredentials()));

    String json = HttpUtils.executeJson(httpClient, httpRequest);
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
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpRequest = HttpUtils.createRequest(URLFormatter.getAllLamps(hueBridge.getHueBridgeCredentials().getIpAddress()),
            HttpUtils.createCredentialHeader(hueBridge.getHueBridgeCredentials()));

    String json = HttpUtils.executeJson(httpClient, httpRequest);
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
