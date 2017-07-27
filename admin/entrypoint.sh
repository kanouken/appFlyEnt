#!/bin/sh
chmod 777 /*.jar
java -server -Xmx500m -Xms500m  -Djava.security.egd=file:/dev/./urandom -jar /app.jar  --spring.profiles.active=pro

