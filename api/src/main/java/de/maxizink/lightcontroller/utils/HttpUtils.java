package de.maxizink.lightcontroller.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.maxizink.lightcontroller.body.HueRequestBody;
import de.maxizink.lightcontroller.discovery.bridge.models.HueBridgeCredentials;
import de.maxizink.lightcontroller.mapper.CustomObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import java.net.URI;

public class HttpUtils {

  private static final ObjectMapper OBJECT_MAPPER = CustomObjectMapper.create();

  public static Header createCredentialHeader(final HueBridgeCredentials hueBridgeCredentials) {
    return new BasicHeader("hue-application-key", hueBridgeCredentials.getUserName());
  }

  @SneakyThrows
  public static String executeJson(final HttpUriRequest httpRequest) {
    return executeJson(createClient(), httpRequest);
  }

  @SneakyThrows
  public static String executeJson(final HttpClient httpClient, final HttpUriRequest httpRequest) {
    HttpResponse httpResponse = httpClient.execute(httpRequest);
    return EntityUtils.toString(httpResponse.getEntity());
  }

  public static HttpClient createClient() {
    return HttpClients.custom()
            .setSSLSocketFactory(new SSLConnectionSocketFactory(TrustEverythingUtil.getContext(), (hostname, session) -> true))
            .setSSLHostnameVerifier((hostname, session) -> true)
            .build();
  }

  public static HttpUriRequest createRequest(final URI uri) {
    return RequestBuilder.get()
            .setUri(uri)
            .build();
  }

  public static HttpUriRequest createRequest(final URI uri, final Header... headers) {
    RequestBuilder requestBuilder = RequestBuilder.get();
    requestBuilder.setUri(uri);
    for (Header header : headers) {
      requestBuilder.addHeader(header);
    }
    return requestBuilder.build();
  }


  @SneakyThrows
  public static HttpUriRequest createPostRequest(final URI uri, final HueRequestBody hueRequestBody) {
    return RequestBuilder.post()
            .setUri(uri)
            .setEntity(new StringEntity(OBJECT_MAPPER.writeValueAsString(hueRequestBody)))
            .build();
  }

  @SneakyThrows
  public static HttpUriRequest createPostRequest(final URI uri, final HueRequestBody hueRequestBody,
                                                 final Header... headers) {
    RequestBuilder requestBuilder = RequestBuilder.post()
            .setUri(uri)
            .setEntity(new StringEntity(OBJECT_MAPPER.writeValueAsString(hueRequestBody)));

    for (Header header : headers) {
      requestBuilder.addHeader(header);
    }

    return requestBuilder.build();
  }

  @SneakyThrows
  public static HttpUriRequest createPutRequest(final URI uri, final HueRequestBody hueRequestBody,
                                                final Header... headers) {
    RequestBuilder requestBuilder = RequestBuilder.put()
            .setUri(uri)
            .setEntity(new StringEntity(OBJECT_MAPPER.writeValueAsString(hueRequestBody)));

    for (Header header : headers) {
      requestBuilder.addHeader(header);
    }

    return requestBuilder.build();
  }
}