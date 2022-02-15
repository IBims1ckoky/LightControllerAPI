package io.github.ibims1ckoky.lightcontroller.discovery.lamp.update;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.HueLamp;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.LampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.body.HueColorBody;
import io.github.ibims1ckoky.lightcontroller.utils.CIEColor;
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
