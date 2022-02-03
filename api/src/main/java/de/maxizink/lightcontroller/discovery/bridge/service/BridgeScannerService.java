package de.maxizink.lightcontroller.discovery.bridge.service;

import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeScanner;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.ServiceAccessor;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

public class BridgeScannerService implements BridgeScanner {

  private final ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

  /**
   * Default this is set to 12.
   * So, the Developer/User hast 12 * 5 Seconds (60 Seconds) to press the
   * Button after Executing the Method.
   */
  private int maxIntervals = 12;

  @Override
  public void setMaxIntervals(int maxIntervals) {
    this.maxIntervals = maxIntervals;
  }

  @Override
  public void scanForHueBridgeCredentials(final String bridgeIp, final Consumer<HueBridgeCredentialsResponse> hueBridgeConsumer) {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    Timer timer = new Timer();

    timer.schedule(new TimerTask() {
      int currentIntervals = 0;

      @Override
      public void run() {
        HueBridgeCredentialsResponse credentialsResponse = bridgeDiscovery.getAPIKey(bridgeIp);
        if (credentialsResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
          timer.cancel();
          hueBridgeConsumer.accept(credentialsResponse);
          return;
        }

        if (currentIntervals >= maxIntervals) {
          timer.cancel();
          hueBridgeConsumer.accept(credentialsResponse);
          return;
        }

        currentIntervals++;
      }
    }, 0, 5000);
  }

  @Override
  public void scanForHueBridgeCredentialsAsync(String bridgeIp, Consumer<HueBridgeCredentialsResponse> hueBridgeConsumer) {
    AsyncBridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(AsyncBridgeDiscovery.class);
    Timer timer = new Timer();

    timer.schedule(new TimerTask() {
      int currentIntervals = 0;

      @Override
      public void run() {
       bridgeDiscovery.getAPIKeyAsync(bridgeIp).thenAccept(hueBridgeCredentialsResponse -> {
          if (hueBridgeCredentialsResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
            timer.cancel();
            hueBridgeConsumer.accept(hueBridgeCredentialsResponse);
            return;
          }

          if (currentIntervals >= maxIntervals) {
            timer.cancel();
            hueBridgeConsumer.accept(hueBridgeCredentialsResponse);
            return;
          }

          currentIntervals++;
        });
      }
    }, 0, 5000);
  }
}
