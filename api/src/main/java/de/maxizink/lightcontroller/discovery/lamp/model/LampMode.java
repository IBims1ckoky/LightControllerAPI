package de.maxizink.lightcontroller.discovery.lamp.model;

public enum LampMode {

  NORMAL,
  STREAMING;

  static LampMode getMode(final String mode) {
    switch (mode.toLowerCase()) {
      case "normal" -> {
        return NORMAL;
      }
      case "streaming" -> {
        return STREAMING;
      }
    }
    return null;
  }


}
