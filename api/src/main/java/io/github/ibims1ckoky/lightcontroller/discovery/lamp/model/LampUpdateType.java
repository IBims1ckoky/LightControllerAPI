package io.github.ibims1ckoky.lightcontroller.discovery.lamp.model;

import io.github.ibims1ckoky.lightcontroller.discovery.lamp.api.LampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.update.BrightnessLampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.update.ColorLampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.update.ColorTemperatureLampUpdate;
import io.github.ibims1ckoky.lightcontroller.discovery.lamp.update.EnabledLampUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.awt.*;

@AllArgsConstructor
@Getter
public final class LampUpdateType<T> {

  public static final LampUpdateType<Color> COLOR = new LampUpdateType<>(ColorLampUpdate.class, Color.class);
  public static final LampUpdateType<Integer> COLOR_TEMPERATURE = new LampUpdateType<>(ColorTemperatureLampUpdate.class, Integer.class);
  public static final LampUpdateType<Boolean> ENABLE = new LampUpdateType<>(EnabledLampUpdate.class, Boolean.class);
  public static final LampUpdateType<Number> BRIGHTNESS = new LampUpdateType<>(BrightnessLampUpdate.class, Number.class);

  private Class<? extends LampUpdate<?>> updateClazz;
  private Class<?> type;

}
