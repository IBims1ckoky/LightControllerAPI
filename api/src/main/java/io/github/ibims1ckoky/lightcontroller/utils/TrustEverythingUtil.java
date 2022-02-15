package io.github.ibims1ckoky.lightcontroller.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

@Slf4j
public class TrustEverythingUtil implements X509TrustManager {

  @Override
  public void checkClientTrusted(X509Certificate[] chain, String authType) {

  }

  @Override
  public void checkServerTrusted(X509Certificate[] chain, String authType) {

  }

  @Override
  public X509Certificate[] getAcceptedIssuers() {
    return new X509Certificate[]{};
  }

  @SneakyThrows
  public static void trustAllSslConnectionsByDisablingCertificateVerification() {
    log.info("Turning off certificate verification...");
    SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, new TrustManager[]{new TrustEverythingUtil()}, new SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    log.info("Certificate verification has been turned off, all certificates are now accepted.");
  }

  @SneakyThrows
  public static SSLContext getContext() {
    SSLContext sslContext = SSLContext.getInstance("SSL");
    sslContext.init(null, new TrustManager[]{new TrustEverythingUtil()}, new SecureRandom());
    return sslContext;
  }

}