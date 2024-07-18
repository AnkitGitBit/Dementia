# Stage 1: compile a JAR file
FROM maven:3.8.5-openjdk-17-slim as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

# Stage 2: run the previously built JAR file
FROM openjdk:17-jdk-alpine

COPY --from=builder /app/target/Hackathon-Dementia-*.jar /Hackathon-Dementia.jar

# Run the web service on container startup.
CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Dserver.port=${PORT}","-jar","/Hackathon-Dementia.jar"]
