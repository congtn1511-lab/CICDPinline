FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY src/libs src/libs
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]