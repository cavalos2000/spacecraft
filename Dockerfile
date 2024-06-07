# Use the official Maven image with JDK 21 to build the application
FROM maven:3.8.8-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/spacecraft-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SERVICE_SECURITY_USER_USERNAME=user
ENV SERVICE_SECURITY_USER_PASSWORD=pass
ENV SERVICE_SECURITY_USER_ROLE=USER

ENTRYPOINT ["java", "-jar", "app.jar"]
