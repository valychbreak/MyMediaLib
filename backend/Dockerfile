FROM openjdk:13-jdk-alpine

VOLUME /tmp
ARG JAR_FILE=./target/MyMediaLib.jar
COPY ${JAR_FILE} app.jar
COPY ./wait-postgres.sh wait-postgres.sh

ENTRYPOINT ["sh", "wait-postgres.sh"]