package de.maxizink.lightcontroller.discovery.lamp.model;

public enum LampArchetype {

  UNKNOWN_ARCHETYPE,
  CLASSIC_BULB,
  SULTAN_BULB,
  FLOOD_BULB,
  /**
   * Implemented {@link de.maxizink.lightcontroller.discovery.lamp.api.HueLamp}
   */
  SPOT_BULB,
  CANDLE_BULB,
  LUSTER_BULB,
  PENDANT_ROUND,
  PENDANT_LONG,
  CEILING_ROUND,
  CEILING_SQUARE,
  FLOOR_SHADE,
  FLOOR_LANTERN,
  TABLE_SHADE,
  RECESSED_CEILING,
  RECESSED_FLOOR,
  SINGLE_SPOT,
  DOUBLE_SPOT,
  TABLE_WASH,
  WALL_LANTERN,
  WALL_SHADE,
  FLEXIBLE_LAMP,
  GROUND_SPOT,
  WALL_SPOT,
  PLUG,
  HUE_GO,
  HUE_LIGHTSTRIP,
  HUE_IRIS,
  HUE_BLOOM,
  BOLLARD,
  WALL_WASHER,
  HUE_PLAY,
  VINTAGE_BULB,
  CHRISTMAS_TREE,
  HUE_CENTRIS,
  HUE_LIGHTSTRIP_TV,
  HUE_TUBE,
  HUE_SIGNE;

  public static LampArchetype getArchetype(final String value) {
    return LampArchetype.valueOf(value.toUpperCase());
  }

}
