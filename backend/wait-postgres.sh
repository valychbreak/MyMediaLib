#!/bin/sh

while ! nc -z host.docker.internal 1111; do sleep 1; done;
echo "Waited. Done now!!";

java -Djava.security.egd=file:/dev/./urandom -jar /app.jar