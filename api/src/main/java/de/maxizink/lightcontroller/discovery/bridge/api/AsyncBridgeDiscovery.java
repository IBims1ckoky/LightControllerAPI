package de.maxizink.lightcontroller.discovery.bridge.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AsyncBridgeDiscovery {

  CompletableFuture<String> getBridgeIp();

  CompletableFuture<List<String>> getAllBridgeIps();

}
