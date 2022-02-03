<a href="https://playlegend.net"><img src="assets/light-api.png" width="100%"></a>

[![LightController](https://jitpack.io/v/IBims1ckoky/LightControllerAPI.svg)](https://jitpack.io/#IBims1ckoky/LightControllerAPI)
[![MissingFeature](https://img.shields.io/badge/Missing%20Feature-Open%20an%20Issue-blueviolet?style=flat-square&logo=appveyor)](https://img.shields.io/badge/Missing%20Feature-Open%20an%20Issue-blueviolet?style=flat-square&logo=appveyor)

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