package io.github.ibims1ckoky.lightcontroller.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtils {

  public static JsonObject fromJsonArray(final String responseBody) {
    JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();
    return jsonArray.get(0).getAsJsonObject();
  }

  public static JsonObject fromJson(final String responseBody) {
    return JsonParser.parseString(responseBody).getAsJsonObject();
  }

}
