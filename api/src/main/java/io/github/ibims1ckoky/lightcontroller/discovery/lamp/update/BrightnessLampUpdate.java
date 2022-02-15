package io.github.ibims1ckoky.lightcontroller.discovery.lamp.update;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.LampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.body.HueLampBrightnessBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BrightnessLampUpdate implements LampUpdate<Number> {

  private final HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(final Number... param) {
    return new HueLampBrightnessBody(param[0]);
  }

}
