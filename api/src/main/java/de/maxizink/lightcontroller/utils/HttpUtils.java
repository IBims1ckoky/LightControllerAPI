package de.maxizink.lightcontroller.utils;

import java.net.URI;
import java.net.http.HttpRequest;

public class HttpUtils {

  public static HttpRequest createRequest(final URI uri) {
    return HttpRequest.newBuilder()
            .uri(uri)
            .build();
  }

}
