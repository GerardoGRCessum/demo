FROM eclipse-temurin:21.0.2_13-jdk-jammy

WORKDIR /app

#COPY ./target/demo-0.0.1-SNAPSHOT.jar ./
COPY ./pom.xml /app
COPY ./.mvn ./.mvn
COPY ./mvnw .
COPY ./pom.xml .
                        #saltar compilacion de test y main
RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline segunda manera
COPY ./src ./src
RUN ./mvnw clean package -DskipTests
EXPOSE 8001

#ENTRYPOINT ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "./target/demo-0.0.1-SNAPSHOT.jar"]
