package io.github.ibims1ckoky.lightcontroller.discovery.bridge.api;

import io.github.ibims1ckoky.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import io.github.ibims1ckoky.lightcontroller.injection.Service;

public interface HueBridgeDiscovery extends Service {

  HueBridge discoverHueBridge(final HueBridgeCredentials hueBridgeCredentials);

}