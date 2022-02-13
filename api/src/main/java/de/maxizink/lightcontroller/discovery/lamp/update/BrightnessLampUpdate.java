package de.maxizink.lightcontroller.discovery.lamp.update;

import de.maxizink.lightcontroller.body.HueRequestBody;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.body.HueLampBrightnessBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrightnessLampUpdate implements LampUpdate<Number> {

  private final HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(final Number... param) {
    return new HueLampBrightnessBody(param[0]);
  }

}
