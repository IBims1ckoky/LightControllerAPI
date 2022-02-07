package de.maxizink.lightcontroller.discovery.lamp.api;

import de.maxizink.lightcontroller.body.HueRequestBody;

public interface LampUpdate<T> {

  HueRequestBody getRequestBody(final T... param);

}
