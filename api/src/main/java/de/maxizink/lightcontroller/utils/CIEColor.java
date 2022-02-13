package de.maxizink.lightcontroller.utils;

import lombok.Data;

import java.awt.*;

@Data
public class CIEColor {

  private final float x;
  private final float y;
  private final int brightness;

  public static CIEColor converterColorToCIE(final Color color) {
    float red = color.getRed();
    float green = color.getGreen();
    float blue = color.getBlue();

    double redGamma = gamma(red);
    double greenGama = gamma(green);
    double blueGama = gamma(blue);

    double rgbX = redGamma * 0.664511f + greenGama * 0.154324f + blueGama * 0.162028f;
    double rgbY = redGamma * 0.283881f + greenGama * 0.668433f + blueGama * 0.047685f;
    double rgbZ = redGamma * 0.000088f + greenGama * 0.072310f + blueGama * 0.986039f;

    float x = (float) (rgbX / (rgbX + rgbY + rgbZ));
    float y = (float) (rgbY / (rgbX + rgbY + rgbZ));
    return new CIEColor(x, y, (int) (rgbY * 255f));
  }

  private static double gamma(float component) {
    return (component > 0.04045f) ? Math.pow((component + 0.055f) / (1.0f + 0.055f), 2.4f) : (component / 12.92f);
  }

}
