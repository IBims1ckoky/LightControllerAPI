package io.github.ibims1ckoky.lightcontroller.discovery.lamp.body;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HueColorTemperatureBody implements HueRequestBody {

  private ColorTemperature color_temperature;

  public HueColorTemperatureBody(final int mirek) {
    if (mirek < 135 || mirek > 500) {
      throw new IllegalArgumentException("int is not between 135 and 500");
    }
    this.color_temperature = new ColorTemperature(mirek);
  }

  @Data
  public static class ColorTemperature {
    private final int mirek;
  }

}
