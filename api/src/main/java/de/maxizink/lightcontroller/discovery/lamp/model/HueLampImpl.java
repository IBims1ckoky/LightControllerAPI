package de.maxizink.lightcontroller.discovery.lamp.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLamp;
import de.maxizink.lightcontroller.discovery.lamp.api.HueLampDiscovery;
import de.maxizink.lightcontroller.discovery.lamp.api.LampUpdate;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import de.maxizink.lightcontroller.injection.ServiceAccessor;
import de.maxizink.lightcontroller.utils.HttpUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static de.maxizink.lightcontroller.utils.HttpUtils.createCredentialHeader;
import static de.maxizink.lightcontroller.utils.HttpUtils.executeJson;

@Getter
public class HueLampImpl implements HueLamp {

  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  private UUID id;
  private String id_v1;
  private Alert alert;
  private @Nullable Color color;
  private LampMode lampMode;
  private Dimming dimming;
  private LampType lampType;
  private MetaData metaData;
  @Deprecated(since = "hue-api-bride-buggy")
  private boolean on;

  private final HueBridge hueBridge;

  @SneakyThrows
  public HueLampImpl(final JsonObject dataObject, final HueBridge hueBridge) {
    this.hueBridge = hueBridge;

    this.id = UUID.fromString(dataObject.get("id").getAsString());
    this.id_v1 = dataObject.get("id_v1").getAsString();
    this.on = dataObject.getAsJsonObject("on").get("on").getAsBoolean();
    this.alert = OBJECT_MAPPER.readValue(dataObject.getAsJsonObject("alert").toString(), Alert.class);
    this.lampMode = LampMode.getMode(dataObject.get("mode").getAsString());
    this.dimming = new Dimming(dataObject.getAsJsonObject("dimming").get("brightness").getAsNumber());

    if (dataObject.has("color")) {
      this.color = OBJECT_MAPPER.readValue(dataObject.getAsJsonObject("color").toString(), Color.class);
      this.lampType = LampType.COLOR;
    } else {
      this.lampType = LampType.NO_COLOR;
    }

    JsonObject metaDataObject = dataObject.getAsJsonObject("metadata");
    this.metaData = new MetaData(LampArchetype.getArchetype(metaDataObject.get("archetype").getAsString()),
            metaDataObject.get("name").getAsString());
  }

  @Override
  public void refreshLampState() {
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);
    JsonObject jsonObject = hueLampDiscovery.getLampObject(this);

    HueLamp hueLamp = new HueLampImpl(jsonObject, hueBridge);
    if (jsonObject.has("color")) {
      this.color = hueLamp.getColor();
    }

    this.on = hueLamp.isOn();
    this.lampMode = hueLamp.getLampMode();
    this.alert = hueLamp.getAlert();
    this.metaData = hueLamp.getMetaData();
    this.dimming = new Dimming(jsonObject.getAsJsonObject("dimming").get("brightness").getAsNumber());
  }

  @SneakyThrows
  @Override
  public <T> void updateSync(final LampUpdateType<T> lampUpdateType, final T... t) {
    HueLampDiscovery hueLampDiscovery = ServiceAccessor.accessService(HueLampDiscovery.class);

    Constructor<?> constructor = lampUpdateType.getUpdateClazz().getConstructor(HueLamp.class);
    LampUpdate<T> lampUpdate = (LampUpdate<T>) constructor.newInstance(this);

    HueBridgeCredentials hueBridgeCredentials = getHueBridge().getHueBridgeCredentials();
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpRequest = HttpUtils.createPutRequest(
            URLFormatter.getLamp(hueBridgeCredentials.getIpAddress(), this.getId()),
            lampUpdate.getRequestBody(t),
            createCredentialHeader(hueBridgeCredentials)
    );
    executeJson(httpClient, httpRequest);
    refreshLampState();
  }

  @Override
  public <T> CompletableFuture<Void> updateAsync(final LampUpdateType<T> lampUpdateType, final T... t) {
    return CompletableFuture.runAsync(() -> updateSync(lampUpdateType, t));
  }

  @Override
  public String getName() {
    return this.metaData.getName();
  }

  @Override
  public HueBridge getHueBridge() {
    return hueBridge;
  }
}