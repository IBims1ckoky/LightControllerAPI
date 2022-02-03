package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeDiscoveryException;
import de.maxizink.lightcontroller.discovery.bridge.response.BridgeIpDiscoveryResponse;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import de.maxizink.lightcontroller.utils.HttpUtils;
import lombok.SneakyThrows;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class AsyncBridgeDiscoveryService implements AsyncBridgeDiscovery {

  private final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public CompletableFuture<String> getBridgeIp() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(BridgeDiscovery.HUE_BRIDGE_DISCOVERY_URI);

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
              BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(response.body());
              if (bridgeIpDiscoveryResponse.length <= 0) {
                throw new HueBridgeDiscoveryException("No Hue Bridge was found");
              }
              return bridgeIpDiscoveryResponse[0].getAddress();
            });
  }

  @Override
  public CompletableFuture<List<String>> getAllBridgeIps() {
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest = HttpUtils.createRequest(BridgeDiscovery.HUE_BRIDGE_DISCOVERY_URI);

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(response -> {
              BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(response.body());
              return Arrays.stream(bridgeIpDiscoveryResponse)
                      .map(BridgeIpDiscoveryResponse::getAddress)
                      .collect(Collectors.toList());
            });
  }

  @SneakyThrows
  private BridgeIpDiscoveryResponse[] getBridges(final String response) {
    return OBJECT_MAPPER.readValue(response, BridgeIpDiscoveryResponse[].class);
  }

}
