FROM openjdk:17-alpine AS build-stage

WORKDIR /app

COPY pom.xml .
COPY src/main/java .
COPY src/main/resources .

RUN mvn clean install

# Switch to a lightweight runtime image
FROM openjdk:17-slim AS runtime-stage

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8080

# Start the backend application
CMD ["java", "-jar", "app.jar"]

#build
#docker build -t newsapp-backend-image .
