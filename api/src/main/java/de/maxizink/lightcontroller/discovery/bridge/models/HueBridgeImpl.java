package de.maxizink.lightcontroller.discovery.bridge.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.maxizink.lightcontroller.discovery.bridge.api.HueBridge;
import de.maxizink.lightcontroller.discovery.bridge.exception.HueBridgeInformationDiscoveryException;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import de.maxizink.lightcontroller.utils.HttpUtils;
import de.maxizink.lightcontroller.utils.URLFormatter;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHeader;

import java.net.URI;

@Getter
public class HueBridgeImpl implements HueBridge {

  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  private final String dataUri;
  private final HueBridgeCredentials hueBridgeCredentials;
  private final BridgeInfo bridgeInfo;

  public HueBridgeImpl(final HueBridgeCredentials hueBridgeCredentials) {
    this.hueBridgeCredentials = hueBridgeCredentials;
    this.dataUri = "https://" + hueBridgeCredentials.getIpAddress() + "/clip/v2/resource/bridge";
    this.bridgeInfo = fetchBridgeInfo();
  }

  @SneakyThrows
  private BridgeInfo fetchBridgeInfo() {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpUriRequest = HttpUtils.createRequest(URLFormatter.getBridgeConfig(hueBridgeCredentials.getIpAddress()));
    String json = HttpUtils.executeJson(httpClient, httpUriRequest);
    return OBJECT_MAPPER.readValue(json, BridgeInfo.class);
  }

  @SneakyThrows
  private JsonArray getDataArray() {
    HttpClient httpClient = HttpUtils.createClient();
    HttpUriRequest httpUriRequest = HttpUtils.createRequest(URI.create(dataUri),
            new BasicHeader(HttpHeaders.HOST, this.hueBridgeCredentials.getIpAddress()),
            new BasicHeader("hue-application-key", this.hueBridgeCredentials.getUserName())
    );

    String json = HttpUtils.executeJson(httpClient, httpUriRequest);
    return checkErrors(JsonParser.parseString(json).getAsJsonObject());
  }

  private JsonArray checkErrors(final JsonObject jsonObject) {
    JsonArray errorElement = jsonObject.get("errors").getAsJsonArray();
    if (errorElement.size() >= 1) {
      throw new HueBridgeInformationDiscoveryException("An error occurred while fetch data from the Bridge");
    }

    return jsonObject.get("data").getAsJsonArray();
  }


}
