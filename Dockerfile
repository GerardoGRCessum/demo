FROM eclipse-temurin:21.0.2_13-jdk-jammy

WORKDIR /app

COPY ./target/demo-0.0.1-SNAPSHOT.jar ./

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
