# STAGE 1: Build stage
FROM maven:3.9.9-eclipse-temurin-24 AS builder
WORKDIR /main
COPY pom.xml /main/pom.xml
COPY src/ /main/src
RUN mvn clean package -DSkipTests

# STAGE 2: Runtime stage
FROM eclipse-temurin:24-jre
WORKDIR /app
COPY start.sh /app/start.sh
COPY --from=builder /main/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["/bin/bash","start.sh","/app/app.jar"]