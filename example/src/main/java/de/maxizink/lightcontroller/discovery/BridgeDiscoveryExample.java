package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.models.BridgeInfo;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.ServiceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BridgeDiscoveryExample {


  public BridgeDiscoveryExample() {
    //discoverBridgeIP(); //This shows all Methods to get the BridgeIP
    //createHueBridgeCredentials(); //This should create an API Key
    // getHueBridgeFromExistingKey(); //This should create a HueBridge Class
  }

  /**
   * This shows the Methods from the {@link BridgeDiscovery} and the {@link AsyncBridgeDiscovery} to
   * get the BridgeIP(s).
   */
  @SneakyThrows
  private void discoverBridgeIP() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    AsyncBridgeDiscovery asyncBridgeDiscovery = ServiceAccessor.accessService(AsyncBridgeDiscovery.class);

    String bridgeIp = bridgeDiscovery.discoverBridgeIP(); //Sync
    List<String> bridgeIps = bridgeDiscovery.discoverAllBridgeIPs(); //Sync

    String asyncBridgeIp = asyncBridgeDiscovery.discoveryBridgeIPAsync().get(); //Async
    List<String> asyncBridgeIps = asyncBridgeDiscovery.discoverAllBridgeIPsAsync().get(); //Async
  }

  /**
   * This shows the Methods from the {@link BridgeDiscovery} to generate {@link HueBridgeCredentials} for a BridgeIP.
   * The {@link AsyncBridgeDiscovery} has the same Methods but only async.
   */
  @SneakyThrows
  private void createHueBridgeCredentials() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    String bridgeIp = bridgeDiscovery.discoverBridgeIP();

    HueBridgeCredentialsResponse keyGenerateResponse = bridgeDiscovery.generateHueBridgeCredentials(bridgeIp);
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
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    String bridgeIp = bridgeDiscovery.discoverBridgeIP();
    HueBridgeCredentialsResponse hueBridgeCredentials = bridgeDiscovery.generateHueBridgeCredentials(bridgeIp);
    if (hueBridgeCredentials.getRespone().equals(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED)) {
      // Credentials could not created
      return;
    }

    HueBridge hueBridge = bridgeDiscovery.discoverHueBridge(hueBridgeCredentials.getHueBridgeCredentials());
    BridgeInfo bridgeInfo = hueBridge.getBridgeInfo();
    log.info("Bridge MAC-Adress: " + bridgeInfo.getMac());
  }

}
