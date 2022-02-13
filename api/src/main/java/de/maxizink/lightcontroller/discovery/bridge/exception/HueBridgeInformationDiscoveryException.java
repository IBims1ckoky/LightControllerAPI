package de.maxizink.lightcontroller.discovery.bridge.exception;

public class HueBridgeInformationDiscoveryException extends RuntimeException{

  public HueBridgeInformationDiscoveryException(final String message) {
    super(message);
  }

  public HueBridgeInformationDiscoveryException(final String message, final Throwable throwable) {
    super(message, throwable);
  }


}
