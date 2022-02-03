package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AsyncBridgeDiscovery extends Service {

  CompletableFuture<String> getBridgeIpAsync();

  CompletableFuture<List<String>> getAllBridgeIpsAsync();

  CompletableFuture<HueBridgeCredentialsResponse> getAPIKeyAsync(final String bridgeIp);

}
