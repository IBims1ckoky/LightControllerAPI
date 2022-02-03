package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.body.GenerateHueBridgeBridgeBody;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeImpl;
import de.maxizink.lightcontroller.discovery.bridge.response.BridgeIpDiscoveryResponse;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import de.maxizink.lightcontroller.utils.HttpUtils;
import de.maxizink.lightcontroller.utils.JsonUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeDiscoveryService implements BridgeDiscovery {

  private static final URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");
  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public String discoverBridgeIP() {
    return discoverAllBridgeIPs().get(0);
  }

  @SneakyThrows
  @Override
  public HueBridgeCredentialsResponse generateHueBridgeCredentials(final String bridgeIp) {
    HttpClient httpClient = HttpUtils.createClient();
    HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
    HttpUriRequest httpUriRequest = HttpUtils.createPostRequest(URLFormatter.getBridgeAPIKey(bridgeIp), hueRequestBody);

    HttpResponse httpResponse = httpClient.execute(httpUriRequest);
    String body = EntityUtils.toString(httpResponse.getEntity());

    JsonObject jsonObject = JsonUtils.fromJsonArray(body);
    if (jsonObject.has("error")) {
      return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED);
    }

    JsonObject credentialsObject = jsonObject.getAsJsonObject("success");
    HueBridgeCredentials hueBridgeCredentials = getCredentials(credentialsObject);
    hueBridgeCredentials.setIpAddress(bridgeIp);
    return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.GENERATED, hueBridgeCredentials);
  }

  @Override
  public HueBridge discoverHueBridge(final HueBridgeCredentials hueBridgeCredentials) {
    return new HueBridgeImpl(hueBridgeCredentials);
  }

  @SneakyThrows
  @Override
  public List<String> discoverAllBridgeIPs() {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpUriRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

    HttpResponse response = httpClient.execute(httpUriRequest);
    String body = EntityUtils.toString(response.getEntity());

    BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = OBJECT_MAPPER.readValue(body, BridgeIpDiscoveryResponse[].class);
    return Arrays.stream(bridgeIpDiscoveryResponse).
            map(BridgeIpDiscoveryResponse::getAddress)
            .collect(Collectors.toList());
  }

  private HueBridgeCredentials getCredentials(final JsonObject credentialsObject) {
    JsonElement userName = credentialsObject.get("username");
    JsonElement clientKey = credentialsObject.get("clientkey");
    return new HueBridgeCredentials(userName.getAsString(), clientKey.getAsString(), "");
  }
}