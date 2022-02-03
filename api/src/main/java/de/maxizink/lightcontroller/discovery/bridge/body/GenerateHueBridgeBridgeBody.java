package de.maxizink.lightcontroller.discovery.bridge.body;

import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GenerateHueBridgeBridgeBody implements HueBridgeRequestBody {

  private String devicetype;
  private boolean generateclientkey;

}
