package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeDiscoveryException;
import de.maxizink.lightcontroller.discovery.bridge.response.BridgeDiscoveryResponse;
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
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(BridgeDiscovery.HUE_BRIDGE_DISCOVERY_URI);

    HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    BridgeDiscoveryResponse[] bridgeDiscoveryResponse = OBJECT_MAPPER.readValue(response.body(), BridgeDiscoveryResponse[].class);
    if (bridgeDiscoveryResponse.length <= 0) {
      throw new HueBridgeDiscoveryException("No Hue Bridge was found");
    }
    return bridgeDiscoveryResponse[0].getInternalipaddress();
  }

  @SneakyThrows
  @Override
  public List<String> getAllBridgeIps() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(BridgeDiscovery.HUE_BRIDGE_DISCOVERY_URI);

    HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    BridgeDiscoveryResponse[] bridgeDiscoveryResponse = OBJECT_MAPPER.readValue(response.body(), BridgeDiscoveryResponse[].class);
    return Arrays.stream(bridgeDiscoveryResponse).
            map(BridgeDiscoveryResponse::getInternalipaddress)
            .collect(Collectors.toList());
  }
}