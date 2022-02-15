package io.github.ibims1ckoky.lightcontroller.discovery.lamp.update;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.LampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.body.HueColorTemperatureBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ColorTemperatureLampUpdate implements LampUpdate<Integer> {

  private final HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(Integer... param) {
    return new HueColorTemperatureBody(param[0]);
  }
}
