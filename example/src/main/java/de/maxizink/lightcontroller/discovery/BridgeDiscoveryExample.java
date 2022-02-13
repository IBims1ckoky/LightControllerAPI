package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.BridgeCredentialsDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeIpDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.models.BridgeInfo;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.injection.ServiceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BridgeDiscoveryExample {


  public BridgeDiscoveryExample() {
    //discoverBridgeIP(); //This shows all Methods to get the BridgeIP
    //createHueBridgeCredentials(); //This should create an API Key
    //getHueBridgeFromExistingKey(); //This should create a HueBridge Class
  }

  /**
   * This shows the Methods from the {@link BridgeIpDiscovery} to get the BridgeIP(s).
   */
  @SneakyThrows
  private void discoverBridgeIP() {
    BridgeIpDiscovery bridgeIpDiscovery = ServiceAccessor.accessService(BridgeIpDiscovery.class);

    String bridgeIp = bridgeIpDiscovery.discoverBridgeIP(); //Sync
    List<String> bridgeIps = bridgeIpDiscovery.discoverAllBridgeIPs(); //Sync

    String asyncBridgeIp = bridgeIpDiscovery.discoveryBridgeIPAsync().get(); //Async
    List<String> asyncBridgeIps = bridgeIpDiscovery.discoverAllBridgeIPsAsync().get(); //Async
  }

  /**
   * This shows the Methods from the {@link BridgeCredentialsDiscovery} to generate{@link HueBridgeCredentials} for a BridgeIP.
   */
  @SneakyThrows
  private void createHueBridgeCredentials() {
    BridgeIpDiscovery bridgeIpDiscovery = ServiceAccessor.accessService(BridgeIpDiscovery.class);
    BridgeCredentialsDiscovery bridgeCredentialsDiscovery = ServiceAccessor.accessService(BridgeCredentialsDiscovery.class);
    String bridgeIp = bridgeIpDiscovery.discoverBridgeIP();

    HueBridgeCredentialsResponse keyGenerateResponse = bridgeCredentialsDiscovery.generateHueBridgeCredentials(bridgeIp);
    if (keyGenerateResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED)) {
      //Handle here what should happen if the link button was not pressed
      //INFO: The getCredentials() Method returns null and throws an error!
      log.info("No Credentials generated! Press the Link Button");
    }

    if (keyGenerateResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
      HueBridgeCredentials hueBridgeCredentials = keyGenerateResponse.getHueBridgeCredentials();
      //Do Stuff here you want if the Credentials were generated!
      log.info("Credentials were generated:");
      log.info("   - UserName  -> " + hueBridgeCredentials.getUserName());
      log.info("   - ClientKey -> " + hueBridgeCredentials.getClientKey());
    }
  }


  @SneakyThrows
  private void getHueBridgeFromExistingKey() {
    BridgeIpDiscovery bridgeIpDiscovery = ServiceAccessor.accessService(BridgeIpDiscovery.class);
    BridgeCredentialsDiscovery bridgeCredentialsDiscovery = ServiceAccessor.accessService(BridgeCredentialsDiscovery.class);
    HueBridgeDiscovery hueBridgeDiscovery = ServiceAccessor.accessService(HueBridgeDiscovery.class);

    String bridgeIp = bridgeIpDiscovery.discoverBridgeIP();
    HueBridgeCredentialsResponse hueBridgeCredentials = bridgeCredentialsDiscovery.generateHueBridgeCredentials(bridgeIp);
    if (hueBridgeCredentials.getRespone().equals(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED)) {
      // Credentials could not created
      return;
    }

    HueBridge hueBridge = hueBridgeDiscovery.discoverHueBridge(hueBridgeCredentials.getHueBridgeCredentials());
    BridgeInfo bridgeInfo = hueBridge.getBridgeInfo();
    log.info("Bridge MAC-Adress: " + bridgeInfo.getMac());
  }

}
