package io.github.ibims1ckoky.lightcontroller.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class HueResponseValidator {

  public static boolean hasErrors(final JsonObject jsonObject) {
    if (!jsonObject.has("errors")) {
      return false;
    }
    JsonArray errorArray = jsonObject.getAsJsonArray("errors");
    return errorArray.size() >= 1;
  }
}
