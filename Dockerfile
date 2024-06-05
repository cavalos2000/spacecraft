# Use the official Maven image with JDK 21 to build the application
FROM maven:3.8.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and source code to the container
COPY pom.xml .
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Use the official Eclipse Temurin JDK 21 image for the runtime
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/spacecraft-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
