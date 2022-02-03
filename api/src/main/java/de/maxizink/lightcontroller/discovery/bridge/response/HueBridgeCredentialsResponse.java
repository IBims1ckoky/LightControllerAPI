package de.maxizink.lightcontroller.discovery.bridge.response;

import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
@NoArgsConstructor
public class HueBridgeCredentialsResponse {

  private Respone respone;
  private @Nullable HueBridgeCredentials hueBridgeCredentials;

  public HueBridgeCredentialsResponse(Respone respone) {
    this.respone = respone;
  }

  public HueBridgeCredentials getHueBridgeCredentials() {
    if (respone.equals(Respone.LINK_BUTTON_NOT_PRESSED)) {
      throw new NullPointerException("There Response is LINK_BUTTON_NOT_PRESSED, so there are no Credentials!");
    }
    return hueBridgeCredentials;
  }

  public Respone getRespone() {
    return respone;
  }

  public enum Respone {

    LINK_BUTTON_NOT_PRESSED,
    GENERATED;

  }

}
