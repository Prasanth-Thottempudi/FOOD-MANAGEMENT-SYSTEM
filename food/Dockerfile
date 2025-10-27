# Use a lightweight OpenJDK 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set environment variables
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Set work directory inside container
WORKDIR /app

# Copy the Maven-built JAR into the container
COPY target/food-0.0.1-SNAPSHOT.jar app.jar

# Expose Spring Boot port
EXPOSE 9092

# Run the JAR
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
