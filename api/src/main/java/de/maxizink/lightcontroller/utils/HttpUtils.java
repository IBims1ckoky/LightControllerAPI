package de.maxizink.lightcontroller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxizink.lightcontroller.body.HueBridgeRequestBody;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpUtils {

  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  public static HttpRequest createRequest(final URI uri) {
    return HttpRequest.newBuilder()
            .uri(uri)
            .build();
  }

  @SneakyThrows
  public static HttpRequest createPostRequest(final URI uri, final HueBridgeRequestBody hueRequestBody) {
    return HttpRequest.newBuilder()
            .uri(uri)
            .POST(HttpRequest.BodyPublishers.ofString(OBJECT_MAPPER.writeValueAsString(hueRequestBody)))
            .build();
  }


}
