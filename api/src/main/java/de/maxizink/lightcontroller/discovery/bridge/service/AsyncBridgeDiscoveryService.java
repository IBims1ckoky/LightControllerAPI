package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.body.GenerateHueBridgeBridgeBody;
import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import de.maxizink.lightcontroller.discovery.bridge.response.BridgeIpDiscoveryResponse;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import de.maxizink.lightcontroller.utils.HttpUtils;
import de.maxizink.lightcontroller.utils.HueBridgeURLUtils;
import de.maxizink.lightcontroller.utils.JsonUtils;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AsyncBridgeDiscoveryService implements AsyncBridgeDiscovery {

  private static final URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");
  private final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public CompletableFuture<String> getBridgeIpAsync() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
              BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(response.body());
              if (bridgeIpDiscoveryResponse.length <= 0) {
                throw new HueBridgeIpDiscoveryException("No Hue Bridge was found");
              }
              return bridgeIpDiscoveryResponse[0].getAddress();
            });
  }

  @Override
  public CompletableFuture<List<String>> getAllBridgeIpsAsync() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
              BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(response.body());
              return Arrays.stream(bridgeIpDiscoveryResponse)
                      .map(BridgeIpDiscoveryResponse::getAddress)
                      .collect(Collectors.toList());
            });
  }

  @Override
  public CompletableFuture<HueBridgeCredentialsResponse> getAPIKeyAsync(String bridgeIp) {
    HttpClient httpClient = HttpClient.newHttpClient();
    HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
    HttpRequest httpRequest = HttpUtils.createPostRequest(HueBridgeURLUtils.getBridgeAPIKey(bridgeIp), hueRequestBody);

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
              JsonObject jsonObject = JsonUtils.fromJsonArray(response.body());
              if (jsonObject.has("error")) {
                return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED);
              }

              JsonObject credentialsObject = jsonObject.getAsJsonObject("success");
              HueBridgeCredentials hueBridgeCredentials = getCredentials(credentialsObject);
              return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.GENERATED, hueBridgeCredentials);
            });
  }

  @SneakyThrows
  private BridgeIpDiscoveryResponse[] getBridges(final String response) {
    return OBJECT_MAPPER.readValue(response, BridgeIpDiscoveryResponse[].class);
  }

  private HueBridgeCredentials getCredentials(final JsonObject credentialsObject) {
    JsonElement userName = credentialsObject.get("username");
    JsonElement clientKey = credentialsObject.get("clientkey");
    return new HueBridgeCredentials(userName.getAsString(), clientKey.getAsString());
  }

}
