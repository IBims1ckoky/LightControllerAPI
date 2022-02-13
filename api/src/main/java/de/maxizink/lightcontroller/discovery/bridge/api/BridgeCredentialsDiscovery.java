package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.injection.Service;

import java.util.concurrent.CompletableFuture;

public interface BridgeCredentialsDiscovery extends Service {

  HueBridgeCredentialsResponse generateHueBridgeCredentials(final String bridgeIp);

  CompletableFuture<HueBridgeCredentialsResponse> generateHueBridgeCredentialsAsync(final String bridgeIp);

}
