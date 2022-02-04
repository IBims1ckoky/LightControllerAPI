package de.maxizink.lightcontroller.discovery.bridge.body;

import de.maxizink.lightcontroller.body.HueRequestBody;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GenerateHueBridgeBody implements HueRequestBody {

  private String devicetype;
  private boolean generateclientkey;

}
