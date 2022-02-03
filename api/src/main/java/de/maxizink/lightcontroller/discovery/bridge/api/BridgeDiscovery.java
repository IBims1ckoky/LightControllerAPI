package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeIpDiscoveryException;
import de.maxizink.lightcontroller.service.Service;
import lombok.SneakyThrows;

import java.net.URI;
import java.util.List;

public interface BridgeDiscovery extends Service {

  URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");

  @SneakyThrows
  static URI getBridgeAPIKey(final String bridgeIp) {
    return new URI("http://" + bridgeIp + "/api");
  }

  String getBridgeIp() throws HueBridgeIpDiscoveryException;

  String getAPIKey(final String bridgeIp);

  List<String> getAllBridgeIps() throws HueBridgeIpDiscoveryException;

}