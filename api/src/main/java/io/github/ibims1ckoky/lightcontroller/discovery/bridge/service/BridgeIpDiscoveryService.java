package io.github.ibims1ckoky.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.response.BridgeIpDiscoveryResponse;
import io.github.ibims1ckoky.lightcontroller.mapper.CustomObjectMapper;
import io.github.ibims1ckoky.lightcontroller.utils.HttpUtils;
import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class BridgeIpDiscoveryService implements BridgeIpDiscovery {

  private static final URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");
  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @SneakyThrows
  @Override
  public String discoverBridgeIP() {
    return discoverAllBridgeIPs().get(0);
  }

  @SneakyThrows
  @Override
  public List<String> discoverAllBridgeIPs() {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpUriRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

    String body = HttpUtils.executeJson(httpClient, httpUriRequest);

    BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = OBJECT_MAPPER.readValue(body, BridgeIpDiscoveryResponse[].class);
    return Arrays.stream(bridgeIpDiscoveryResponse).
            map(BridgeIpDiscoveryResponse::getAddress)
            .collect(Collectors.toList());
  }

  @SneakyThrows
  @Override
  public CompletableFuture<String> discoveryBridgeIPAsync() {
    return CompletableFuture.supplyAsync(() -> {
      HttpClient httpClient = HttpUtils.createClient();
      HttpUriRequest httpRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

      String body = HttpUtils.executeJson(httpClient, httpRequest);
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
      HttpClient httpClient = HttpUtils.createClient();
      HttpUriRequest httpRequest = HttpUtils.createRequest(HUE_BRIDGE_DISCOVERY_URI);

      String body = HttpUtils.executeJson(httpClient, httpRequest);
      BridgeIpDiscoveryResponse[] bridgeIpDiscoveryResponse = getBridges(body);
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
