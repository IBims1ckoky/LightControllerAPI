package io.github.ibims1ckoky.lightcontroller.discovery.bridge.api;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import io.github.ibims1ckoky.lightcontroller.injection.Service;

import java.util.concurrent.CompletableFuture;

public interface BridgeCredentialsDiscovery extends Service {

  HueBridgeCredentialsResponse generateHueBridgeCredentials(final String bridgeIp);

  CompletableFuture<HueBridgeCredentialsResponse> generateHueBridgeCredentialsAsync(final String bridgeIp);

}
