package io.github.ibims1ckoky.lightcontroller.discovery.lamp.api;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;

public interface LampUpdate<T> {

  HueRequestBody getRequestBody(final T... param);

}
