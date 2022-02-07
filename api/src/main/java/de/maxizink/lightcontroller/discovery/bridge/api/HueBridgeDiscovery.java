package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.injection.Service;

public interface HueBridgeDiscovery extends Service {

  HueBridge discoverHueBridge(final HueBridgeCredentials hueBridgeCredentials);

}