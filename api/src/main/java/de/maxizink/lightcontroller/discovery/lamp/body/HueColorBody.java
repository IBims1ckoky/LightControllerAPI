package de.maxizink.lightcontroller.discovery.lamp.body;

import de.maxizink.lightcontroller.body.HueRequestBody;
import lombok.Data;

public class HueColorBody implements HueRequestBody {

  private CustomColor color;

  public HueColorBody(double x, double y) {
    this.color = new CustomColor(x, y);
  }

  @Data
  public static class CustomColor {

    private final ColorEntry xy;

    public CustomColor(double x, double y) {
      this.xy = new ColorEntry(x, y);
    }

    @Data
    private static class ColorEntry {

      private final double x;
      private final double y;

    }

  }

}
