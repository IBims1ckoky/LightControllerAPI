package de.maxizink.lightcontroller.discovery.bridge.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.body.GenerateHueBridgeBridgeBody;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.utils.HttpUtils;
import de.maxizink.lightcontroller.utils.JsonUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.concurrent.CompletableFuture;

import static de.maxizink.lightcontroller.utils.HttpUtils.*;

public class BridgeCredentialsDiscoveryService implements BridgeCredentialsDiscovery {


  @SneakyThrows
  @Override
  public HueBridgeCredentialsResponse generateHueBridgeCredentials(final String bridgeIp) {
    HttpClient httpClient = HttpUtils.createClient();
    HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
    HttpUriRequest httpUriRequest = HttpUtils.createPostRequest(URLFormatter.getBridgeAPIKey(bridgeIp), hueRequestBody);

    String body = HttpUtils.executeJson(httpClient, httpUriRequest);

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
  public CompletableFuture<HueBridgeCredentialsResponse> generateHueBridgeCredentialsAsync(final String bridgeIp) {
    return CompletableFuture.supplyAsync(() -> {
      HttpClient httpClient = createClient();
      HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
      HttpUriRequest httpUriRequest = createPostRequest(URLFormatter.getBridgeAPIKey(bridgeIp), hueRequestBody);

      String body = executeJson(httpClient, httpUriRequest);
      JsonObject jsonObject = JsonUtils.fromJsonArray(body);
      if (jsonObject.has("error")) {
        return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED);
      }

      JsonObject credentialsObject = jsonObject.getAsJsonObject("success");
      HueBridgeCredentials hueBridgeCredentials = getCredentials(credentialsObject);
      hueBridgeCredentials.setIpAddress(bridgeIp);
      return new HueBridgeCredentialsResponse(HueBridgeCredentialsResponse.Respone.GENERATED, hueBridgeCredentials);
    });
  }

  private HueBridgeCredentials getCredentials(final JsonObject credentialsObject) {
    JsonElement userName = credentialsObject.get("username");
    JsonElement clientKey = credentialsObject.get("clientkey");
    return new HueBridgeCredentials(userName.getAsString(), clientKey.getAsString(), "");
  }

}
