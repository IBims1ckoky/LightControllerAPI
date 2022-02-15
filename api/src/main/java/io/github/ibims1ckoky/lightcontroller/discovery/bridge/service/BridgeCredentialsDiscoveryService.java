package io.github.ibims1ckoky.lightcontroller.discovery.bridge.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.body.GenerateHueBridgeBody;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import io.github.ibims1ckoky.lightcontroller.utils.HttpUtils;
import io.github.ibims1ckoky.lightcontroller.utils.JsonUtils;
import io.github.ibims1ckoky.lightcontroller.utils.URLFormatter;
import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.concurrent.CompletableFuture;

import static io.github.ibims1ckoky.lightcontroller.utils.HttpUtils.*;

public class BridgeCredentialsDiscoveryService implements BridgeCredentialsDiscovery {


  @SneakyThrows
  @Override
  public HueBridgeCredentialsResponse generateHueBridgeCredentials(final String bridgeIp) {
    HttpClient httpClient = HttpUtils.createClient();
    HueRequestBody hueRequestBody = GenerateHueBridgeBody.of("LightControllerAPI#2022", true); //TODO user should set the Application Name (devicetype)
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
      HttpClient httpClient = HttpUtils.createClient();
      HueRequestBody hueRequestBody = GenerateHueBridgeBody.of("LightControllerAPI#2022", true);
      HttpUriRequest httpUriRequest = createPostRequest(URLFormatter.getBridgeAPIKey(bridgeIp), hueRequestBody);

      String body = HttpUtils.executeJson(httpClient, httpUriRequest);
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
