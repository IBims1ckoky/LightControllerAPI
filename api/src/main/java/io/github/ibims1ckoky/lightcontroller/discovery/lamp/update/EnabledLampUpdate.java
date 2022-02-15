package io.github.ibims1ckoky.lightcontroller.discovery.lamp.update;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.LampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.body.HueLampEnabledBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnabledLampUpdate implements LampUpdate<Boolean> {

  public HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(final Boolean... param) {
    return new HueLampEnabledBody(param[0]);
  }

}
