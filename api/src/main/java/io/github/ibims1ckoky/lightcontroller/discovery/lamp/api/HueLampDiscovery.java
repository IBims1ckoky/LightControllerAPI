package io.github.ibims1ckoky.lightcontroller.discovery.lamp.api;

import com.google.gson.JsonObject;
import io.github.ibims1ckoky.lightcontroller.discovery.bridge.api.HueBridge;
import io.github.ibims1ckoky.lightcontroller.injection.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HueLampDiscovery extends Service {

  List<HueLamp> discoverAllLamps(final HueBridge hueBridge);

  CompletableFuture<List<HueLamp>> discoverAllLampsAsync(final HueBridge hueBridge);

  JsonObject getLampObject(final HueLamp hueLamp);

}
