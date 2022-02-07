package de.maxizink.lightcontroller.discovery.bridge.exception;

public class HueBridgeIpDiscoveryException extends RuntimeException {

  public HueBridgeIpDiscoveryException(final String message) {
    super(message);
  }

  public HueBridgeIpDiscoveryException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

}
