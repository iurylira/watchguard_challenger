FROM docker.io/amazoncorretto:21-al2023

unset DOCKER_CERT_PATH
unset DOCKER_TLS_VERIFY


WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 8484

ENTRYPOINT ["java", "-jar", "app.jar"]

