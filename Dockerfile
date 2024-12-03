FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY build/libs/main-service-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
