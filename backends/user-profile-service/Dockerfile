FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/user-profile-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]