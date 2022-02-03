package de.maxizink.lightcontroller.discovery;

import de.maxizink.lightcontroller.discovery.bridge.api.AsyncBridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.BridgeDiscovery;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.bridge.response.HueBridgeCredentialsResponse;
import de.maxizink.lightcontroller.service.ServiceAccessor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class BridgeDiscoveryExample {


  public BridgeDiscoveryExample() {
    discoverBridgeIP(); //This shows all Methods to get the BridgeIP
    createHueBridgeCredentials(); //This should create an API Key
  }

  /**
   * This shows the Methods from the {@link BridgeDiscovery} and the {@link AsyncBridgeDiscovery} to
   * get the BridgeIP(s).
   */
  @SneakyThrows
  private void discoverBridgeIP() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    AsyncBridgeDiscovery asyncBridgeDiscovery = ServiceAccessor.accessService(AsyncBridgeDiscovery.class);

    String bridgeIp = bridgeDiscovery.getBridgeIp(); //Sync
    List<String> bridgeIps = bridgeDiscovery.getAllBridgeIps(); //Sync

    String asyncBridgeIp = asyncBridgeDiscovery.getBridgeIpAsync().get(); //Async
    List<String> asyncBridgeIps = asyncBridgeDiscovery.getAllBridgeIpsAsync().get(); //Async
  }

  /**
   * This shows the Methods from the {@link BridgeDiscovery} to generate {@link HueBridgeCredentials} for a BridgeIP.
   * The {@link AsyncBridgeDiscovery} has the same Methods but only async.
   */
  @SneakyThrows
  private void createHueBridgeCredentials() {
    BridgeDiscovery bridgeDiscovery = ServiceAccessor.accessService(BridgeDiscovery.class);
    String bridgeIp = bridgeDiscovery.getBridgeIp();

    HueBridgeCredentialsResponse keyGenerateResponse = bridgeDiscovery.getAPIKey(bridgeIp);
    if (keyGenerateResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.LINK_BUTTON_NOT_PRESSED)) {
      //Handle here what should happen if the link button was not pressed
      //INFO: The getCredentials() Method returns null and throws an error!
      System.out.println("No Credentials generated! Press the Link Button");
    }

    if (keyGenerateResponse.getRespone().equals(HueBridgeCredentialsResponse.Respone.GENERATED)) {
      HueBridgeCredentials hueBridgeCredentials = keyGenerateResponse.getHueBridgeCredentials();
      //Do Stuff here you want if the Credentials were generated!
      System.out.println("Credentials were generated:");
      System.out.println("   - UserName  -> " + hueBridgeCredentials.getUserName());
      System.out.println("   - ClientKey -> " + hueBridgeCredentials.getClientKey());
    }


  }

}
