package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.models.BridgeInfo;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;

import java.util.List;
import java.util.Optional;

public interface HueBridge {

  List<HueLamp> getLamps();

  Optional<HueLamp> getLampByName(final String name);

  BridgeInfo getBridgeInfo();

  HueBridgeCredentials getHueBridgeCredentials();

}
