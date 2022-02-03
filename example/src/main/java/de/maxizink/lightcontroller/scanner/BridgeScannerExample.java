package de.maxizink.lightcontroller.scanner;

import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeScanner;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.ServiceAccessor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BridgeScannerExample {

  public BridgeScannerExample() {
    startScanning(); //This shows all Methods to scan for Bridge Credentials
  }

  void startScanning() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    String bridgeIp = bridgeDiscovery.discoverBridgeIP();

    BridgeScanner bridgeScanner = ServiceAccessor.accessService(BridgeScanner.class);
    bridgeScanner.scanForHueBridgeCredentials(bridgeIp, hueBridgeCredentialsResponse -> {
      if (hueBridgeCredentialsResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED)) {
        System.out.println("Link button was not pressed in the last minute");
      }

      if (hueBridgeCredentialsResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
        HueBridgeCredentials hueBridgeCredentials = hueBridgeCredentialsResponse.getHueBridgeCredentials();

        System.out.println("Credentials were generated after some time:");
        System.out.println("   - UserName  -> " + hueBridgeCredentials.getUserName());
        System.out.println("   - ClientKey -> " + hueBridgeCredentials.getClientKey());
      }
    });
  }

}
