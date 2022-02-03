package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.Service;
import lombok.SneakyThrows;

import java.net.URI;
import java.util.List;

public interface BridgeDiscovery extends Service {

  String getBridgeIp() throws HueBridgeIpDiscoveryException;

  List<String> getAllBridgeIps() throws HueBridgeIpDiscoveryException;

  HueBridgeCredentialsResponse getAPIKey(final String bridgeIp);

}