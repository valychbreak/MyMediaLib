FROM openjdk:13-jdk-alpine

VOLUME /tmp
ARG JAR_FILE=./target/MyMediaLib.jar
COPY ${JAR_FILE} app.jar
COPY entrypoint.sh entrypoint.sh

ENTRYPOINT ["sh", "entrypoint.sh"]