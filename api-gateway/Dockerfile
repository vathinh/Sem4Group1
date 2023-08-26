# Use a base image with Java and other necessary tools
FROM openjdk:11-jre-slim

# Set working directory
WORKDIR /app

# Copy the built JAR file into the image
COPY target/api-gateway-service-1.0-SNAPSHOT.jar .

# Expose the port your application listens on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "api-gateway.jar"]
