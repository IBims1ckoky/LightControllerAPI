package io.github.ibims1ckoky.lightcontroller.discovery.bridge.body;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class GenerateHueBridgeBody implements HueRequestBody {

  private String devicetype;
  private boolean generateclientkey;

}
