package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.body.GenerateHueBridgeBridgeBody;
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
import java.util.stream.Collectors;

public class BridgeDiscoveryService implements BridgeDiscovery {

  private static final URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");

  private final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public String getBridgeIp() {
    return getAllBridgeIps().get(0);
  }

  @SneakyThrows
  @Override
  public HueBridgeCredentialsResponse getAPIKey(final String bridgeIp) {
    HttpClient httpClient = HttpClient.newHttpClient();
    HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
    HttpRequest httpRequest = HttpUtils.createPostRequest(HueBridgeURLUtils.getBridgeAPIKey(bridgeIp), hueRequestBody);

    HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    JsonObject jsonObject = JsonUtils.fromJsonArray(response.body());
    if (jsonObject.has("error")) {
      return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED);
    }

    JsonObject credentialsObject = jsonObject.getAsJsonObject("success");
    HueBridgeCredentials hueBridgeCredentials = getCredentials(credentialsObject);
    return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.GENERATED, hueBridgeCredentials);
  }

  @SneakyThrows
  @Override
  public List<String> getAllBridgeIps() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

    HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = OBJECT_MAPPER.readValue(response.body(), BridgeIpDiscoveryResponse[].class);
    return Arrays.stream(bridgeIpDiscoveryResponse).
            map(BridgeIpDiscoveryResponse::getAddress)
            .collect(Collectors.toList());
  }

  private HueBridgeCredentials getCredentials(final JsonObject credentialsObject) {
    JsonElement userName = credentialsObject.get("username");
    JsonElement clientKey = credentialsObject.get("clientkey");
    return new HueBridgeCredentials(userName.getAsString(), clientKey.getAsString());
  }

}