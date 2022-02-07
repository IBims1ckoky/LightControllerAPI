package de.maxizink.lightcontroller.discovery.lamp.api;

import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.lamp.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * This is the Interface from a normal {@link HueLamp}:
 * It can be a colored one or a default Lamp.
 * The default implementation is {@link HueLampImpl}
 */
public interface HueLamp {

  void refreshLampState();

  <T> void updateSync(final LampUpdateType<T> lampUpdateType, T... t);

  <T> CompletableFuture<Void> updateAsync(final LampUpdateType<T> lampUpdateType, T... t);

  /**
   * This returns you if the Lamp is enabled or disabled.
   * But the Bridge handels that stuff a litte bit buggy
   * so i dont recommend to use it while it wasnt fixed by Phillips.
   *
   * @return on/off
   */
  @Deprecated(since = "hue-api-bride-buggy")
  boolean isOn();

  /**
   * This returns you the {@link UUID} of the Lamp in the v2 HueAPI Version.
   * Note that this UUID is only Single when you are using one {@link HueBridge}.
   * With 2 or more {@link HueBridge} the Id can be duplicated.
   *
   * @return you the id
   */
  UUID getId();

  /**
   * This returns you the Id of the Lamp.
   * Its the HueAPI v1 Id (example: /light/1)
   *
   * @return you the old id
   */
  @Deprecated(since = "hue-api-v2-release")
  String getId_v1();

  /**
   * <p>Returns you the Simple Name of the Lamp.
   *
   * @return you the Name of the Lamp
   */
  String getName();

  /**
   * <p> Returns you you the alert Effets.
   *
   * @return the {@link Alert}
   */
  Alert getAlert();

  /**
   * <p> Returns you the Color from the Lamp.
   * <p> The Color format is CIE.
   *
   * @return you the {@link Color}
   */
  Color getColor() throws NullPointerException;

  /**
   * Returns you the current {@link LampMode} of the Lamp.
   * It Returns {@link LampMode}.STREAMING when the Lamp is listening to Something
   * like Spotify, Desktop or something else.
   *
   * @return you the LampMode
   */
  LampMode getLampMode();

  Dimming getDimming();

  /**
   * This returns you which type of Lamp this {@link HueLamp} is.
   * When it can diplay Colors, it is a {@link LampType} COLOR
   *
   * @return you the LampType
   */
  LampType getLampType();

  /**
   * Returns you the MetaData from the {@link HueLamp}.
   * It contains the {@link LampArchetype}. At this time, the api is basically maintained for the {@link LampType}
   * but other types works to.
   * The second thing in the {@link MetaData} is the name of the Lamp which you can get with the
   * {@code getName()} Method.
   *
   * @return you the {@link MetaData}
   */
  MetaData getMetaData();

  HueBridge getHueBridge();

  /**
   * <p> This are the Sub Classes for the {@link HueLamp}
   */
  @Data
  @AllArgsConstructor
  class MetaData {
    private LampArchetype archetype;
    private String name;
  }

  @Data
  class Alert {
    private List<String> action_values;
  }

  @Data
  class Dimming {
    private final Number brightness;
  }

  @Data
  class Color {
    private Map<String, ColorEntry> gamut;
    private String gamut_type;
    private ColorEntry xy;

    @Data
    public static class ColorEntry {
      private double x;
      private double y;
    }
  }
}