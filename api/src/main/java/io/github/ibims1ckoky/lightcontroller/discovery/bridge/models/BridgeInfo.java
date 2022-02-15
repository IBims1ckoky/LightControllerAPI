package io.github.ibims1ckoky.lightcontroller.discovery.bridge.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BridgeInfo {

  private String name;
  private String datastoreversion;
  private String swversion;
  private String apiversion;
  private String mac;
  private String bridgeid;
  private boolean factorynew;
  private @Nullable String replacesbridgeid;
  private String modelid;
  private String startkitid;

}
