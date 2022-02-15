package io.github.ibims1ckoky.lightcontroller.discovery;

public enum ResourceType {

  DEVICE,
  BRIDGE_HOME,
  ROOM,
  ZONE,
  LIGHT,
  BUTTON,
  TEMPERATURE,
  LIGHT_LEVEL,
  MOTION,
  ENTERTAINMENT,
  GROUPED_LIGHT,
  DEVICE_POWER,
  ZIGBEE_BRIDGE_CONNECTIVITY,
  ZIGBEE_CONNECTIVITY,
  ZGP_CONNECTIVITY,
  BRIDGE,
  HOMEKIT,
  SCENE,
  ENTERTAINMENT_CONFIGURATION,
  PUBLIC_IMAGE,
  AUTH_V1,
  BEHAVIOR_SCRIPT,
  BEHAVIOR_INSTANCE,
  GEOFENCE,
  GEOFENCE_CLIENT,
  GEOLOCATION;

  public static ResourceType getResourceType(final String name) {
    return ResourceType.valueOf(name.toUpperCase());
  }

}
