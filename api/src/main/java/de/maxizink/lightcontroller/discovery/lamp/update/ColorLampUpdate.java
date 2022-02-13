package de.maxizink.lightcontroller.discovery.lamp.update;

import de.maxizink.lightcontroller.body.HueRequestBody;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.body.HueColorBody;
import de.maxizink.lightcontroller.utils.CIEColor;
import lombok.AllArgsConstructor;

import java.awt.*;

@AllArgsConstructor
public class ColorLampUpdate implements LampUpdate<Color> {

  private final HueLamp hueLamp;

  @Override
  public HueRequestBody getRequestBody(Color... param) {
    CIEColor color = CIEColor.converterColorToCIE(param[0]);
    return new HueColorBody(color.getX(), color.getY());
  }
}
