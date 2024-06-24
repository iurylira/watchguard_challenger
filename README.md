# Challenger to create a Telemetry Restful API


# Technologies
- Java 17 and higher
- Gradle
- Spring Boot 3
- In-Memory H2

**Everytime the app starts the DB is created.**

Test & Build
It uses Gradle as build tool. Run gradle test or gradle assemble respectively.

**Build Applications**
```sh
$ ./gradlew clean
$ ./gradlew build
```

**Run Applications**
```sh
$ ./gradlew bootRun
```
**To access any API is required to pass the API Key on Header**
***X-WG-Telemetry-Key***
=
***ABC123***


**Base URL**

http://localhost:8080/telemetries


