package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.Service;

import java.util.List;

public interface BridgeDiscovery extends Service {

  String discoverBridgeIP() throws HueBridgeIpDiscoveryException;

  List<String> discoverAllBridgeIPs() throws HueBridgeIpDiscoveryException;

  HueBridgeCredentialsResponse generateHueBridgeCredentials(final String bridgeIp);

  HueBridge discoverHueBridge(final HueBridgeCredentials hueBridgeCredentials);

}