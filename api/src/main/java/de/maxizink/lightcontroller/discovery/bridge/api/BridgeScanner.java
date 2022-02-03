package de.maxizink.lightcontroller.discovery.bridge.api;

import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.Service;

import java.util.function.Consumer;

public interface BridgeScanner extends Service {

  void setMaxIntervals(final int maxIntervals);

  void scanForHueBridgeCredentials(final String bridgeIp, final Consumer<HueBridgeCredentialsResponse> hueBridgeConsumer);

  void scanForHueBridgeCredentialsAsync(final String bridgeIp, final Consumer<HueBridgeCredentialsResponse> hueBridgeConsumer);

}