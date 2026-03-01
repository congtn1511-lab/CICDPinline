FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY src/libs /app/src/libs
COPY target/*.jar app.jar

# Tạo tmp dir trước khi dùng
RUN mkdir -p /app/tmp && chmod 777 /app/tmp

EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-Djava.io.tmpdir=/app/tmp"

ENTRYPOINT ["java","-jar","app.jar"]