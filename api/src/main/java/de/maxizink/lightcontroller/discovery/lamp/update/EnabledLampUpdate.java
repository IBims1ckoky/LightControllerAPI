package de.maxizink.lightcontroller.discovery.lamp.update;

import de.maxizink.lightcontroller.body.HueRequestBody;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.body.HueLampEnabledBody;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EnabledLampUpdate implements LampUpdate<Boolean> {

  public HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(final Boolean... param) {
    return new HueLampEnabledBody(param[0]);
  }

}
