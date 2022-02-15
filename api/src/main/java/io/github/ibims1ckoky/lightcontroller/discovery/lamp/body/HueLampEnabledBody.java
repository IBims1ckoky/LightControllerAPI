package io.github.ibims1ckoky.lightcontroller.discovery.lamp.body;

import io.github.ibims1ckoky.lightcontroller.body.HueRequestBody;
import lombok.Data;

@Data
public class HueLampEnabledBody implements HueRequestBody {

  private EnabledStatus on;

  public HueLampEnabledBody(final boolean enabled) {
    this.on = new EnabledStatus(enabled);
  }

  @Data
  public static class EnabledStatus {

    //Real Data
    private final boolean on;

  }

}
