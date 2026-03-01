FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Tạo volume /tmp để giải quyết vấn đề hệ thống tệp Read-only
# Giúp Tomcat/Spring Boot giải nén và ghi file tạm
VOLUME /tmp

# Copy các thư viện ngoài nếu bạn để trong folder src/libs
COPY src/libs /app/src/libs

# Copy file jar đã build từ máy runner (Self-hosted) vào container
COPY target/*.jar app.jar

# Tạo một user thường để chạy ứng dụng (tăng tính bảo mật giống OpenShift)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080
ENV JAVA_TOOL_OPTIONS="-Djava.io.tmpdir=/app/tmp"
# Chạy ứng dụng với tham số tối ưu hóa cho file tạm
ENTRYPOINT ["java", "-jar", "app.jar"]