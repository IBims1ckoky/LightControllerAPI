package de.maxizink.lightcontroller.discovery.lamp.api;

import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.service.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface HueLampDiscovery extends Service {

  List<HueLamp> discoverAllLamps(final HueBridge hueBridge);

  CompletableFuture<List<HueLamp>> discoverAllLampsAsync(final HueBridge hueBridge);

  JsonObject getLampObject(final HueLamp hueLamp);

}
