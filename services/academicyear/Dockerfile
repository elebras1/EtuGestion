FROM openjdk:17-jdk-slim

VOLUME /tmp

COPY build/libs/demo-1.1.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]