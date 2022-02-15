package io.github.ibims1ckoky.lightcontroller.discovery.lamp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum HueColor {

  RED(0.675, 0.322),
  GREEN(0.24, 0.42),
  YELLOW(0.45, 0.5),
  VIOLET(0.08, 0.2),
  ORANGE(0.5, 0.42),
  BLUE(00.18, 0.15);

  private double x;
  private double y;

  }
