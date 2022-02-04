package de.maxizink.lightcontroller.discovery.lamp.update;

import de.maxizink.lightcontroller.body.HueRequestBody;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.body.HueColorTemperatureBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ColorTemperatureLampUpdate implements LampUpdate<Integer> {

  private final HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(Integer... param) {
    return new HueColorTemperatureBody(param[0]);
  }
}
