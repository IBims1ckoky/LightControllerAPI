<a href="https://playlegend.net"><img src="assets/light-api.png" width="60%"></a>

[![LightController](https://jitpack.io/v/IBims1ckoky/LightControllerAPI.svg)](https://jitpack.io/#IBims1ckoky/LightControllerAPI)

# LightControllerAPI

This is an easy to use **LightControllerAPI** in for Java to control Lights from [PhillipsHue](https://www.philips-hue.com/).


# How to add to Project

Gradle:

Maven:

## Example

This is how you can get your BridgeIPs
```java

BridgeDiscovery bridgeDiscovery = new BridgeDiscoveryService();
log.info("Sync - SingleBridgeIP: " + bridgeDiscovery.getBridgeIp());

AsyncBridgeDiscovery asyncBridgeDiscovery = new AsyncBridgeDiscoveryService();
log.info("Async - BridgeIP: " + asyncBridgeDiscovery.getBridgeIp().get());

```