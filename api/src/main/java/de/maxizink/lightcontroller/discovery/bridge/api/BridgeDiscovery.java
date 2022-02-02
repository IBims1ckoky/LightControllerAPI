package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeDiscoveryException;

import java.net.URI;
import java.util.List;

public interface BridgeDiscovery {

  URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");

  String getBridgeIp() throws HueBridgeDiscoveryException;

  List<String> getAllBridgeIps();

}
