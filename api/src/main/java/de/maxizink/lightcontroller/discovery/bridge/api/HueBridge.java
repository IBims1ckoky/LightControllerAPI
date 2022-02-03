package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.models.BridgeInfo;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;

public interface HueBridge {

  BridgeInfo getBridgeInfo();

  HueBridgeCredentials getHueBridgeCredentials();

}
