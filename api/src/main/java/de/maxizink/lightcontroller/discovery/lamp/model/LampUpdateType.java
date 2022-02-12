package de.maxizink.lightcontroller.discovery.lamp.model;

import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.update.BrightnessLampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.update.ColorLampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.update.ColorTemperatureLampUpdate;
import de.maxizink.lightcontroller.discovery.lamp.update.EnabledLampUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public final class LampUpdateType<T> {

  public static final LampUpdateType<HueColor> COLOR = new LampUpdateType<>(ColorLampUpdate.class, HueColor.class);
  public static final LampUpdateType<Integer> COLOR_TEMPERATURE = new LampUpdateType<>(ColorTemperatureLampUpdate.class, Integer.class);
  public static final LampUpdateType<Boolean> ENABLE = new LampUpdateType<>(EnabledLampUpdate.class, Boolean.class);
  public static final LampUpdateType<Number> BRIGHTNESS = new LampUpdateType<>(BrightnessLampUpdate.class, Number.class);

  private Class<? extends LampUpdate<?>> updateClazz;
  private Class<?> type;

}
