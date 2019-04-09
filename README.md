
# SensorPlatform Client

[![Build Status](https://travis-ci.org/jlor/sensorplatform-client.svg?branch=develop)](https://travis-ci.org/jlor/sensorplatform-client) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=sh.x00%3Asensorplatform.client&metric=alert_status)](https://sonarcloud.io/dashboard?id=sh.x00%3Asensorplatform.client) [![Test Coverage](https://sonarcloud.io/api/project_badges/measure?project=sh.x00:sensorplatform.client&metric=coverage)](https://sonarcloud.io/dashboard?id=sh.x00:sensorplatform.client)

## Build

```bash
mvn clean install
```
Copy the resulting file from ```target/sensorplatform-client*.jar``` and ```target/lib``` to your raspberry pi.

Make sure you have Java8 JRE installed on the pi and run the client:
```
./sensorplatform-client-*-runner.jar
```
