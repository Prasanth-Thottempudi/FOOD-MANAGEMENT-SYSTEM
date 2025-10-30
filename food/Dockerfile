# Use a lightweight OpenJDK 17 image
FROM eclipse-temurin:17-jdk-alpine

# Set work directory
WORKDIR /app

# Copy the JAR from the Maven target folder
COPY target/food-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 9092

# Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
