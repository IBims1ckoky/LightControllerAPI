package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.service.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AsyncBridgeDiscovery extends Service {

  CompletableFuture<String> getBridgeIp();

  CompletableFuture<List<String>> getAllBridgeIps();

}
