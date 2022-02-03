package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.body.GenerateHueBridgeBridgeBody;
import de.maxizink.lightcontroller.discovery.bridge.response.BridgeIpDiscoveryResponse;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import de.maxizink.lightcontroller.utils.HttpUtils;
import lombok.SneakyThrows;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BridgeDiscoveryService implements BridgeDiscovery {

  private final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public String getBridgeIp() {
    return getAllBridgeIps().get(0);
  }

  @SneakyThrows
  @Override
  public String getAPIKey(final String bridgeIp) {
    HttpClient httpClient = HttpClient.newHttpClient();
    HueBridgeRequestBody hueRequestBody = GenerateHueBridgeBridgeBody.of("LightControllerAPI#2022", true);
    HttpRequest httpRequest = HttpUtils.createPostRequest(BridgeDiscovery.getBridgeAPIKey(bridgeIp), hueRequestBody);

    HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    System.out.println("Status: " + response.statusCode());
    System.out.println("Response: " + response.body());


    return null;
  }

  @SneakyThrows
  @Override
  public List<String> getAllBridgeIps() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(BridgeDiscovery.HUE_BRIDGE_DISCOVERY_URI);

    HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = OBJECT_MAPPER.readValue(response.body(), BridgeIpDiscoveryResponse[].class);
    return Arrays.stream(bridgeIpDiscoveryResponse).
            map(BridgeIpDiscoveryResponse::getAddress)
            .collect(Collectors.toList());
  }


}