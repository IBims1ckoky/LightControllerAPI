package de.maxizink.lightcontroller.discovery.bridge.exception;

public class HueBridgeDiscoveryException extends RuntimeException {

  public HueBridgeDiscoveryException(final String message) {
    super(message);
  }

  public HueBridgeDiscoveryException(final String message, final Throwable throwable) {
    super(message, throwable);
  }

}
