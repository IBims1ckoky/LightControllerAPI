<a href="https://playlegend.net"><img src="assets/light-api.png" width="100%"></a>

[![LightController](https://jitpack.io/v/IBims1ckoky/LightControllerAPI.svg)](https://jitpack.io/#IBims1ckoky/LightControllerAPI)
[![MissingFeature](https://img.shields.io/badge/Missing%20Feature-Open%20an%20Issue-blueviolet?style=flat-square&logo=appveyor)](https://img.shields.io/badge/Missing%20Feature-Open%20an%20Issue-blueviolet?style=flat-square&logo=appveyor)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=IBims1ckoky_LightControllerAPI&metric=coverage)](https://sonarcloud.io/summary/new_code?id=IBims1ckoky_LightControllerAPI)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=IBims1ckoky_LightControllerAPI&metric=security_rating)](https://sonarcloud.io/summary/new_code?id=IBims1ckoky_LightControllerAPI)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=IBims1ckoky_LightControllerAPI&metric=bugs)](https://sonarcloud.io/summary/new_code?id=IBims1ckoky_LightControllerAPI)    

# LightControllerAPI

This is an easy to use **LightControllerAPI** in for Java to control Lights from [PhillipsHue](https://www.philips-hue.com/).


# How to get started

Gradle (Default):
```gradle
repositories {
   maven { url 'https://jitpack.io' }
}

dependencies {
   implementation 'com.github.IBims1ckoky:LightControllerAPI:Tag'
}
```
  
Maven:
```maven
<repositories>
   <repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
   </repository>
</repositories>
  
<dependencies>
  <dependency>
     <groupId>com.github.IBims1ckoky</groupId>
     <artifactId>LightControllerAPI</artifactId>
     <version>master-SNAPSHOT</version>  
  </dependency>
</dependencies>
```

## Example (Better Example + Wiki Comming Soon)

This is how you can get your BridgeIPs
```java

BridgeDiscovery bridgeDiscovery = new BridgeDiscoveryService();
log.info("Sync - SingleBridgeIP: " + bridgeDiscovery.getBridgeIp());

AsyncBridgeDiscovery asyncBridgeDiscovery = new AsyncBridgeDiscoveryService();
log.info("Async - BridgeIP: " + asyncBridgeDiscovery.getBridgeIp().get());

```
