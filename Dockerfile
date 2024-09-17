FROM openjdk:22-jdk-slim

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY certs/client/ca.pem /certs/client/ca.pem

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

