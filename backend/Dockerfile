FROM openjdk:12.0.1-jdk-oracle
VOLUME /tmp
ARG JAR_FILE=./target/MyMediaLib.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
