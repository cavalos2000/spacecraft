
FROM maven:3.8.4-openjdk-21 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM openjdk:21
WORKDIR /app

COPY --from=build /app/target/spacecraft-0.0.1-SNAPSHOT.jar /app/spacecraft.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "spacecraft.jar"]
