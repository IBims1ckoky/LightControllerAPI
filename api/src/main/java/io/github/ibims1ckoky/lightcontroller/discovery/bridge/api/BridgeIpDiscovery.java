package io.github.ibims1ckoky.lightcontroller.discovery.bridge.api;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import io.github.ibims1ckoky.lightcontroller.injection.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface BridgeIpDiscovery extends Service {

  String discoverBridgeIP() throws HueBridgeIpDiscoveryException;

  List<String> discoverAllBridgeIPs() throws HueBridgeIpDiscoveryException;

  CompletableFuture<String> discoveryBridgeIPAsync();

  CompletableFuture<List<String>> discoverAllBridgeIPsAsync();

}
