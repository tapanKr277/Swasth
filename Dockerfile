FROM ubuntu:latest
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

COPY target/swasth.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "swasth.jar"]
