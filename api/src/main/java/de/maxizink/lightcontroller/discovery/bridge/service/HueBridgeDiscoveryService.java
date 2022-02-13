package de.maxizink.lightcontroller.discovery.bridge.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeImpl;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;

import java.net.URI;

public class HueBridgeDiscoveryService implements HueBridgeDiscovery {

  private static final URI HUE_BRIDGE_DISCOVERY_URI = URI.create("https://discovery.meethue.com/");
  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  @Override
  public HueBridge discoverHueBridge(final HueBridgeCredentials hueBridgeCredentials) {
    return new HueBridgeImpl(hueBridgeCredentials);
  }

  private HueBridgeCredentials getCredentials(final JsonObject credentialsObject) {
    JsonElement userName = credentialsObject.get("username");
    JsonElement clientKey = credentialsObject.get("clientkey");
    return new HueBridgeCredentials(userName.getAsString(), clientKey.getAsString(), "");
  }
}