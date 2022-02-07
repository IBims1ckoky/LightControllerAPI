package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import de.maxizink.lightcontroller.service.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BridgeIpDiscovery extends Service {

  String discoverBridgeIP() throws HueBridgeIpDiscoveryException;

  List<String> discoverAllBridgeIPs() throws HueBridgeIpDiscoveryException;

  CompletableFuture<String> discoveryBridgeIPAsync();

  CompletableFuture<List<String>> discoverAllBridgeIPsAsync();

}
