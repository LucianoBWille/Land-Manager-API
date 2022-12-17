# Using eclipse-temurin:17-jdk-alpine as base image
FROM eclipse-temurin:17-jdk-alpine

# Create a volume to temporary files
VOLUME /tmp

# Copy the builded jar to the image
COPY /build/libs/land-manager-api-0.0.1-SNAPSHOT.jar app.jar

# Set the entrypoint to run the jar
ENTRYPOINT ["java","-jar","/app.jar"]