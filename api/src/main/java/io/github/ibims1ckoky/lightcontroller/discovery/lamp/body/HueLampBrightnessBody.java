package io.github.ibims1ckoky.lightcontroller.discovery.lamp.body;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HueLampBrightnessBody implements HueRequestBody {

  private Dimming dimming;

  public HueLampBrightnessBody(final Number brightness) {
    this.dimming = new Dimming(brightness);
  }

  @Data
  public static class Dimming {

    private final Number brightness;

  }

}
