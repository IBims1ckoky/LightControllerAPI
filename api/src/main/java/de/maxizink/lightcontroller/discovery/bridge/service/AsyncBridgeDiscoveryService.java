package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.BridgeIpDiscoveryResponse;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static de.maxizink.lightcontroller.utils.HttpUtils.*;

public class AsyncBridgeDiscoveryService implements AsyncBridgeDiscovery {

  private static final URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");
  private final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public CompletableFuture<String> discoveryBridgeIPAsync() {
    return CompletableFuture.supplyAsync(() -> {
      HttpClient httpClient = createClient();
      HttpUriRequest httpRequest = createRequest(HUE_BRIDGE_DISCOVERY_URI);

      String body = executeJson(httpClient, httpRequest);
      BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(body);
      if (bridgeIpDiscoveryResponse.length <= 0) {
        throw new HueBridgeIpDiscoveryException("No Hue Bridge was found");
      }
      return bridgeIpDiscoveryResponse[0].getAddress();
    });
  }

  @Override
  public CompletableFuture<List<String>> discoverAllBridgeIPsAsync() {
    return CompletableFuture.supplyAsync(() -> {
      HttpClient httpClient = createClient();
      HttpUriRequest httpRequest = createRequest(HUE_BRIDGE_DISCOVERY_URI);

      String body = executeJson(httpClient, httpRequest);
      BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(body);
      return Arrays.stream(bridgeIpDiscoveryResponse)
              .map(BridgeIpDiscoveryResponse::getAddress)
              .collect(Collectors.toList());
    });
  }

  @Override
  public CompletableFuture<HueBridgeCredentialsResponse> generateHueBridgeCredentialsAsync(final String bridgeIp) {
    /*
    HttpClient httpClient = HttpClient.newHttpClient();
    HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
    HttpRequest httpRequest = HttpUtils.createPostRequest(URLFormatter.getBridgeAPIKey(bridgeIp), hueRequestBody);

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
              JsonObject jsonObject = JsonUtils.fromJsonArray(response.body());
              if (jsonObject.has("error")) {
                return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED);
              }

              JsonObject credentialsObject = jsonObject.getAsJsonObject("success");
              HueBridgeCredentials hueBridgeCredentials = getCredentials(credentialsObject);
              hueBridgeCredentials.setIpAddress(bridgeIp);
              return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.GENERATED, hueBridgeCredentials);
            });

     */
    return null;
  }

  @SneakyThrows
  private BridgeIpDiscoveryResponse[] getBridges(final String response) {
    return OBJECT_MAPPER.readValue(response, BridgeIpDiscoveryResponse[].class);
  }

  private HueBridgeCredentials getCredentials(final JsonObject credentialsObject) {
    JsonElement userName = credentialsObject.get("username");
    JsonElement clientKey = credentialsObject.get("clientkey");
    return new HueBridgeCredentials(userName.getAsString(), clientKey.getAsString(), "");
  }

}
