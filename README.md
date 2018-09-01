
# SensorPlatform Client

[![Build Status](https://travis-ci.org/jlor/sensorplatform-client.svg?branch=develop)](https://travis-ci.org/jlor/sensorplatform-client) [![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=dk.rosenlund.sensorplatform:sensorplatform-client&metric=alert_status)](https://sonarcloud.io/dashboard?id=dk.rosenlund.sensorplatform:sensorplatform-client) [![Test Coverage](https://sonarcloud.io/api/project_badges/measure?project=dk.rosenlund.sensorplatform:sensorplatform-client&metric=coverage)](https://sonarcloud.io/dashboard?id=dk.rosenlund.sensorplatform:sensorplatform-client)

## Build

```bash
mvn clean install
```
Copy the resulting file from ```target/sensorplatform-client.jar``` to your raspberry pi.

Make sure you have Java8 JRE installed on the pi and run the client:
```
./sensorplatform-client.jar
```

## Startup on boot
See [this post](https://www.baeldung.com/spring-boot-app-as-a-service) - it boils down to:
```bash
# For System V init:
sudo ln -s `pwd`/sensorplatform-client.jar /etc/init.d/sensorplatform-client

# For Systemd:
sudo tee /etc/systemd/system/sensorplatform-client.service <<EOF
[Unit]
Description=A Spring Boot application
After=syslog.target

[Service]
User=baeldung
ExecStart=`pwd`/sensorplatform-client.jar SuccessExitStatus=143

[Install]
WantedBy=multi-user.target
EOF

# For Upstart:
mkdir -p ~/.config/upstart
tee ~/.config/upstart/sensorplatform-client.conf <<EOF
description "Sensorplatform Client"
respawn
exec java -jar `pwd`/sensorplatform-client.jar
EOF

# start on boot:
update-rc.d sensorplatform-client.jar defaults
```