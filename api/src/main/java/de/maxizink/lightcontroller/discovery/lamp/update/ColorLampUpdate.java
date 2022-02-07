package de.maxizink.lightcontroller.discovery.lamp.update;

import de.maxizink.lightcontroller.body.HueRequestBody;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.body.HueColorBody;
import de.maxizink.lightcontroller.discovery.lamp.model.HueColor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ColorLampUpdate implements LampUpdate<HueColor> {

  private final HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(HueColor... param) {
    return new HueColorBody(param[0].getX(), param[0].getY());
  }
}
