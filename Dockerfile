FROM adoptopenjdk/openjdk11:jdk-11.0.8_10-alpine
MAINTAINER marcos.filho
VOLUME /tmp
EXPOSE 8080
ADD target/api-0.0.1-SNAPSHOT.jar api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/api.jar"]