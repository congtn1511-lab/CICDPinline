#FROM eclipse-temurin:21-jdk-alpine AS builder
#WORKDIR /app
#ADD target/Jwt-0.0.1-SNAPSHOT.jar app.jar
#
#COPY src/libs src/libs
#EXPOSE 8080
#ENTRYPOINT ["java", \
#    "-Djava.io.tmpdir=/dev/shm ",\
#  "--add-exports", "java.base/sun.security.pkcs10=ALL-UNNAMED", \
#  "--add-exports", "java.base/sun.security.x509=ALL-UNNAMED", \
#  "-jar", "app.jar"]

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY src/libs src/libs
ADD target/*SNAPSHOT.jar app.jar
#ADD ${JAR_FILE} app.jar

EXPOSE 8080
#ENV JAVA_TOOL_OPTIONS="-Djava.io.tmpdir=/dev/shm"
ENTRYPOINT ["sh","-c","java -jar app.jar"]